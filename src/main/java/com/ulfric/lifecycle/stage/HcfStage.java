package com.ulfric.lifecycle.stage;

import com.ulfric.commons.time.TemporalHelper;
import com.ulfric.dragoon.reflect.Classes;
import com.ulfric.servix.services.lifecycle.Stage;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public abstract class HcfStage implements Stage {

	private final Instant end;

	public HcfStage(Instant beginning, Duration duration) {
		Objects.requireNonNull(beginning, "beginning");
		Objects.requireNonNull(duration, "duration");

		this.end = beginning.plus(duration);
	}

	@Override
	public Duration timeRemaining() {
		Duration remaining = TemporalHelper.betweenNowAnd(end);
		return remaining.isNegative() ? Duration.ZERO : remaining;
	}

	@Override
	public String getName() {
		return Classes.getNonDynamic(getClass()).getSimpleName();
	}

}