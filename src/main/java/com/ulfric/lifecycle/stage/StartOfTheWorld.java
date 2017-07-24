package com.ulfric.lifecycle.stage;

import java.time.Duration;
import java.time.Instant;

public class StartOfTheWorld extends HcfStage {

	public StartOfTheWorld(Instant beginning, Duration duration) {
		super(beginning, duration);
	}

}