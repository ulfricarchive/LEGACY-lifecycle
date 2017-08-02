package com.ulfric.lifecycle.scoreboard;

import org.bukkit.entity.Player;

import com.ulfric.monty.Element;
import com.ulfric.monty.Text;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;
import com.ulfric.servix.services.locale.LocaleService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LifecycleElement implements Element {

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
		String key = "scoreboard-lifecycle-" + lowerCaseName;

		String header = LocaleService.getMessage(player, key + "-header",
				Collections.singletonMap("stage", name));
		text.setTitle(header);

		Map<String, String> context = new HashMap<>();
		context.put("stage", stage.getName());
		context.put("remaining", String.valueOf(stage.timeRemaining())); // TODO formatted time
		String body = LocaleService.getMessage(player, key + "-body", context);
		text.setBody(Collections.singletonList(body));

		return text;
	}

}