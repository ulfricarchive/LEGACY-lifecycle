package com.ulfric.lifecycle.scoreboard;

import org.bukkit.entity.Player;

import com.ulfric.i18n.content.Details;
import com.ulfric.monty.element.Element;
import com.ulfric.monty.text.Text;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;
import com.ulfric.servix.services.locale.LocaleService;

import java.util.Collections;

public class LifecycleElement extends Element {

	@Override
	public Text apply(Player player) { // TODO handle new lines
		Stage stage = currentStage();
		if (stage == null) {
			return null;
		}

		return apply(player, stage);
	}

	private Stage currentStage() {
		LifecycleService lifecycle = LifecycleService.get();
		return lifecycle.currentStage();
	}

	private Text apply(Player player, Stage stage) {
		Text text = new Text();

		String name = stage.getName();
		String lowerCaseName = name.toLowerCase();
		String key = "lifecycle-scoreboard-" + lowerCaseName;

		String header = LocaleService.getMessage(player, key + "-header",
				Details.of("stage", name));
		text.setTitle(header);

		Details details = new Details();
		details.add("stage", stage.getName());
		details.add("remaining", String.valueOf(stage.timeRemaining())); // TODO formatted time
		String body = LocaleService.getMessage(player, key + "-body", details);
		text.setBody(Collections.singletonList(body));

		return text;
	}

}