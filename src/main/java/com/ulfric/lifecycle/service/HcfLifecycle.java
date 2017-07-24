package com.ulfric.lifecycle.service;

import org.bukkit.Bukkit;

import com.ulfric.data.config.Configured;
import com.ulfric.data.config.Settings;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.lifecycle.adopter.StageAdopter;
import com.ulfric.lifecycle.model.LifecyclePlan;
import com.ulfric.lifecycle.model.LifecyclePlanEntry;
import com.ulfric.palpatine.Scheduler;
import com.ulfric.palpatine.Task;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.LifecycleStageEvent;
import com.ulfric.servix.services.lifecycle.Stage;

@Configured
public class HcfLifecycle implements LifecycleService {

	@Inject
	private Scheduler scheduler;

	@Settings
	private LifecyclePlanEntry plan;

	private LifecyclePlan currentPlan;
	private Stage currentStage;

	private Task stageTransition;

	@Override
	public Class<LifecycleService> getService() {
		return LifecycleService.class;
	}

	@Override
	public void begin() {
		if (currentStage != null) {
			return;
		}

		currentPlan = plan;
		Stage newStage = StageAdopter.create(currentPlan);
		transition(newStage);
	}

	@Override
	public void nextStage() {
		if (currentPlan == null) {
			begin();
			return;
		}

		LifecyclePlan nextPlan = currentPlan.getNext();
		if (nextPlan == null) {
			currentPlan = null;
			transition(null);
			return;
		}

		Stage newStage = StageAdopter.create(nextPlan);
		currentPlan = nextPlan;
		transition(newStage);
	}

	@Override
	public Stage currentStage() {
		return currentStage;
	}

	private void transition(Stage newStage) {
		LifecycleStageEvent event = new LifecycleStageEvent(this, newStage);
		Bukkit.getPluginManager().callEvent(event);
		currentStage = newStage;

		if (stageTransition != null) {
			stageTransition.cancel();
		}

		if (newStage != null) {
			stageTransition = scheduler.runOnMainThreadLater(this::nextStage, newStage.timeRemaining());
		} else {
			stageTransition = null;
		}
	}

}