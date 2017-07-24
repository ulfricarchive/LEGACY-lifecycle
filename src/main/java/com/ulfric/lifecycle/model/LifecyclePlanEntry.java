package com.ulfric.lifecycle.model;

import java.time.Instant;

public class LifecyclePlanEntry extends LifecyclePlan {

	private Instant scheduledStart;

	public Instant getScheduledStart() {
		return scheduledStart;
	}

	public void setScheduledStart(Instant scheduledStart) {
		this.scheduledStart = scheduledStart;
	}

}