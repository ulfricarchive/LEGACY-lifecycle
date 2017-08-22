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
@Name("begin")
@Permission("lifecycle.begin")
public class LifecycleBeginCommand extends LifecycleCommand {

	@Override
	public void run(Context context) {
		LifecycleService service = LifecycleService.get();
		if (service == null) {
			context.getSender().sendMessage("lifecycle-not-installed");
			return;
		}

		if (service.currentStage() != null) {
			context.getSender().sendMessage("lifecycle-begin-already-started"); // TODO show the current stage?
			return;
		}

		service.begin();
		Stage stage = service.currentStage();
		if (stage == null) {
			context.getSender().sendMessage("lifecycle-begin-no-stage");
			return;
		}

		Details details = Details.of("stage", stage);
		TellService.sendMessage(context.getSender(), "lifecycle-begin", details); // TODO show the current stage?
	}

}