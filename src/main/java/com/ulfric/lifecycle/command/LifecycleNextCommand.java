package com.ulfric.lifecycle.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.Sync;
import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.lifecycle.LifecycleService;
import com.ulfric.servix.services.lifecycle.Stage;
import com.ulfric.servix.services.locale.TellService;

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

		Details details = Details.of("oldStage", oldStage);

		service.nextStage();

		Stage newStage = service.currentStage();
		if (newStage == null) {
			TellService.sendMessage(context.getSender(), "lifecycle-next-end", details);
			return;
		}

		details.add("newStage", newStage);
		TellService.sendMessage(context.getSender(), "lifecycle-next", details); // TODO show the current stage?
	}

}