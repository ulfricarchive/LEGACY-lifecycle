package com.ulfric.lifecycle.command;

import com.ulfric.andrew.Command;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.Sync;
import com.ulfric.commons.naming.Name;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;

import java.util.HashMap;
import java.util.Map;

@Sync
@Name("lifecycle")
@Permission("lifecycle.use")
public class LifecycleCommand implements Command {

	@Override
	public void run(Context context) {
		LifecycleService service = LifecycleService.get();
		if (service == null) {
			context.getSender().sendMessage("lifecycle-not-installed");
			return;
		}

		Stage current = service.currentStage();
		if (current == null) {
			context.getSender().sendMessage("lifecycle-current-not-started");
		} else {
			Map<String, String> details = details();
			details.put("stage", current.getName());
			details.put("timeRemaining", String.valueOf(current.timeRemaining()));
			context.getSender().sendMessage("lifecycle-current", details);
		}
	}

	protected Map<String, String> details() {
		return new HashMap<>();
	}

}