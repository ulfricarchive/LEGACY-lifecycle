package com.ulfric.lifecycle.stage;

import com.ulfric.commons.time.TemporalHelper;
import com.ulfric.lifecycle.adopter.StageAdopter;
import com.ulfric.lifecycle.model.LifecyclePlan;
import com.ulfric.lifecycle.model.LifecyclePlanEntry;
import com.ulfric.servix.services.lifecycle.Stage;

import java.time.Duration;
import java.time.Instant;

public class StartOfTheWorldAdopter extends StageAdopter { // TODO duplicate code with EndOfTheWorldAdopter

	public StartOfTheWorldAdopter() {
		super("StartOfTheWorld");
	}

	@Override
	public Stage apply(LifecyclePlan plan) {
		Instant start = plan instanceof LifecyclePlanEntry ? ((LifecyclePlanEntry) plan).getScheduledStart() : null;
		start = start == null ? TemporalHelper.instantNow() : start; // TODO any other validation?

		Duration duration = plan.getDuration();
		duration = duration == null ? Duration.ZERO : duration; // TODO any other validation?

		return new StartOfTheWorld(start, duration);
	}

}