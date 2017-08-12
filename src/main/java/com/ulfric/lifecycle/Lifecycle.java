package com.ulfric.lifecycle;

import com.ulfric.lifecycle.adopter.StageAdopterFeature;
import com.ulfric.lifecycle.command.LifecycleBeginCommand;
import com.ulfric.lifecycle.command.LifecycleCommand;
import com.ulfric.lifecycle.command.LifecycleNextCommand;
import com.ulfric.lifecycle.scoreboard.LifecycleScoreboardContainer;
import com.ulfric.lifecycle.service.HcfLifecycle;
import com.ulfric.lifecycle.stage.EndOfTheWorldAdopter;
import com.ulfric.lifecycle.stage.StartOfTheWorldAdopter;
import com.ulfric.plugin.Plugin;
import com.ulfric.servix.services.lifecycle.LifecycleService;

public class Lifecycle extends Plugin {

	public Lifecycle() {
		install(StageAdopterFeature.class);

		install(StartOfTheWorldAdopter.class);
		install(EndOfTheWorldAdopter.class);

		install(HcfLifecycle.class);
		install(LifecycleCommand.class);
		install(LifecycleBeginCommand.class);
		install(LifecycleNextCommand.class);

		install(LifecycleScoreboardContainer.class);

		addBootHook(LifecycleService.get()::begin);
	}

}