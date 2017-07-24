package com.ulfric.lifecycle.adopter;

import com.ulfric.dragoon.application.Application;
import com.ulfric.dragoon.application.Feature;

public class StageAdopterFeature extends Feature {

	@Override
	public Application apply(Object stage) {
		return stage instanceof StageAdopter ? new StageAdopterApplication((StageAdopter) stage) : null;
	}

}
