package com.ulfric.lifecycle.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ulfric.commons.value.OptionalHelper;
import com.ulfric.monty.Scoreboard;
import com.ulfric.servix.services.lifecycle.LifecycleStageEvent;

public class LifecycleScoreboardListener implements Listener {

	@EventHandler
	public void on(LifecycleStageEvent event) {
		Bukkit.getOnlinePlayers()
			.stream()
			.map(Scoreboard::getScoreboard)
			.flatMap(OptionalHelper::stream)
			.forEach(scoreboard -> scoreboard.queueUpdate(LifecycleElement.class));
	}

	@EventHandler
	public void on(PlayerJoinEvent event) {
		Scoreboard.getScoreboard(event.getPlayer())
			.ifPresent(scoreboard -> scoreboard.add(new LifecycleElement()));
	}

}