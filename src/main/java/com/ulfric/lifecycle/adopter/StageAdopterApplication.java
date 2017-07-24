package com.ulfric.lifecycle.adopter;

import com.ulfric.dragoon.application.Application;

import java.util.Objects;

public class StageAdopterApplication extends Application {

	private final StageAdopter stage;

	public StageAdopterApplication(StageAdopter stage) {
		Objects.requireNonNull(stage, "stage");

		this.stage = stage;

		addBootHook(this::register);
		addShutdownHook(this::unregister);
	}

	private void register() {
		StageAdopter.register(stage);
	}

	private void unregister() {
		StageAdopter.unregister(stage);
	}

}