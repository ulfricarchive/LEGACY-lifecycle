package com.ulfric.lifecycle.model;

import com.ulfric.commons.value.Bean;

import java.time.Duration;

public class LifecyclePlan extends Bean {

	private String stage;
	private Duration duration;
	private LifecyclePlan next;

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public LifecyclePlan getNext() {
		return next;
	}

	public void setNext(LifecyclePlan next) {
		this.next = next;
	}

}