package com.ulfric.lifecycle.scoreboard;

import com.ulfric.dragoon.application.Container;

public class LifecycleScoreboardContainer extends Container {

	public LifecycleScoreboardContainer() {
		install(LifecycleScoreboardListener.class);
	}

}
