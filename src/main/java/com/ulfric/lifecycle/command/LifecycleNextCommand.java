package com.ulfric.lifecycle.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.Sync;
import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;

@Sync
@Name("next")
@Permission("lifecycle.next")
public class LifecycleNextCommand extends LifecycleCommand {

	@Override
	public void run(Context context) {
		LifecycleService service = LifecycleService.get();
		if (service == null) {
			context.getSender().sendMessage("lifecycle-not-installed");
			return;
		}

		Stage oldStage = service.currentStage();
		if (oldStage == null) {
			context.getSender().sendMessage("lifecycle-next-not-started");
			return;
		}

		Details details = details();
		details.add("oldStage", oldStage.getName());

		service.nextStage();

		Stage newStage = service.currentStage();
		if (newStage == null) {
			context.getSender().sendMessage("lifecycle-next-end", details);
			return;
		}

		details.add("newStage", newStage.getName());
		context.getSender().sendMessage("lifecycle-next", details); // TODO show the current stage?
	}

}