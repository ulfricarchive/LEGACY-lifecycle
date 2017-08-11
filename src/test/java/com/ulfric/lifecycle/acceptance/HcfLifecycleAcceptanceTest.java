package com.ulfric.lifecycle.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.google.common.truth.Truth;

import com.ulfric.botch.Botch;
import com.ulfric.commons.time.TemporalHelper;
import com.ulfric.lifecycle.Lifecycle;
import com.ulfric.lifecycle.adopter.StageAdopter;
import com.ulfric.lifecycle.model.LifecyclePlan;
import com.ulfric.lifecycle.model.LifecyclePlanEntry;
import com.ulfric.lifecycle.service.HcfLifecycle;
import com.ulfric.palpatine.Scheduler;
import com.ulfric.palpatine.Task;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

class HcfLifecycleAcceptanceTest extends Botch<Lifecycle> {

	private final Duration duration = Duration.ofMillis(1);

	private LifecycleService service;
	private Scheduler scheduler;
	private LifecyclePlanEntry plan;
	private StageAdopter stage;

	public HcfLifecycleAcceptanceTest() {
		super(Lifecycle.class);
	}

	@BeforeEach
	void setup() throws Exception {
		plan = new LifecyclePlanEntry();
		service = new HcfLifecycle();
		scheduler = Mockito.mock(Scheduler.class);
		writeField("scheduler", scheduler);
		writeField("plan", plan);

		Mockito.when(scheduler.runOnMainThreadLater(ArgumentMatchers.any(), ArgumentMatchers.any()))
			.then(parameters -> {
				TemporalAmount amount = parameters.getArgument(1);
				long millis = Duration.from(amount).toMillis();
				Thread.sleep(millis);
				Runnable runnable = parameters.getArgument(0);
				runnable.run();
				return Mockito.mock(Task.class);
			});
	}

	@AfterEach
	void unregisterStage() {
		if (stage != null) {
			StageAdopter.unregister(stage);
		}
	}

	private void writeField(String field, Object value) throws Exception {
		Field handle = service.getClass().getDeclaredField(field);
		handle.setAccessible(true);
		handle.set(service, value);
	}

	@Test
	void testFullPlan() {
		stage = stage();
		StageAdopter.register(stage);

		plan.setScheduledStart(TemporalHelper.instantNow().minus(Duration.ofMillis(5)));
		plan.setDuration(duration);
		plan.setStage("next");
		plan.setNext(plans(3));

		service.begin();

		Truth.assertThat(service.currentStage()).isNull();
		Mockito.verify(scheduler, Mockito.atLeast(3)).runOnMainThreadLater(ArgumentMatchers.any(), ArgumentMatchers.eq(duration));
	}

	private StageAdopter stage() {
		return new StageAdopter("next") {
			@Override
			public Stage apply(LifecyclePlan plan) {
				Stage stage = Mockito.mock(Stage.class);
				Instant end = TemporalHelper.instantNowPlus(plan.getDuration());
				Mockito.when(stage.timeRemaining()).then(parameters -> {
					Duration remaining = TemporalHelper.betweenNowAnd(end);
					return remaining.isNegative() ? Duration.ZERO : remaining;
				});
				Mockito.when(stage.toString()).thenReturn(plan.toString());
				return stage;
			}
		};
	}

	private LifecyclePlan plans(int nexts) {
		LifecyclePlan plan = new LifecyclePlan() {
			@Override
			public String toString() {
				return "nexts: " + nexts;
			}
		};

		plan.setDuration(duration);
		plan.setStage("next");

		if (nexts > 0) {
			plan.setNext(plans(nexts - 1));
		}

		return plan;
	}

}