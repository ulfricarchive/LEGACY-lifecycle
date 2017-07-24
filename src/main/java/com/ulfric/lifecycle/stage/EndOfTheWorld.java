package com.ulfric.lifecycle.stage;

import java.time.Duration;
import java.time.Instant;

public class EndOfTheWorld extends HcfStage {

	public EndOfTheWorld(Instant beginning, Duration duration) {
		super(beginning, duration);
	}

}