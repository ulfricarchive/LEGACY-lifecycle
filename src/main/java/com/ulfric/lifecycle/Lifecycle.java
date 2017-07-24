package com.ulfric.lifecycle;

import com.ulfric.platform.Plugin;

public class Lifecycle extends Plugin { // TODO lifecycle on the scoreboard

	public Lifecycle() {
		install(LifecycleContainer.class);
	}

}