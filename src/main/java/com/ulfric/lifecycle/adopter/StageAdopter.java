package com.ulfric.lifecycle.adopter;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.commons.naming.Named;
import com.ulfric.lifecycle.model.LifecyclePlan;
import com.ulfric.servix.services.lifecycle.Stage;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class StageAdopter implements Named, Function<LifecyclePlan, Stage> {

	private static final Map<String, StageAdopter> STAGES = new CaseInsensitiveMap<>();

	public static void register(StageAdopter stage) {
		Objects.requireNonNull(stage, "stage");

		STAGES.put(stage.getName(), stage);
	}

	public static void unregister(StageAdopter stage) {
		Objects.requireNonNull(stage, "stage");

		STAGES.remove(stage.getName(), stage);
	}

	public static Stage create(LifecyclePlan plan) {
		Objects.requireNonNull(plan, "plan");

		String stageName = plan.getStage();
		StageAdopter stage = STAGES.get(stageName);
		Objects.requireNonNull(stage, "stage not found: " + stageName);

		return stage.apply(plan);
	}

}