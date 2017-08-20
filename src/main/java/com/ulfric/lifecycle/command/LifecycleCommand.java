package com.ulfric.lifecycle.command;

import com.ulfric.andrew.Command;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.Sync;
import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;

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
			Details details = details();
			details.add("stage", current.getName());
			details.add("timeRemaining", String.valueOf(current.timeRemaining()));
			context.getSender().sendMessage("lifecycle-current", details);
		}
	}

	protected Details details() {
		return new Details();
	}

}