package commands;

import core.Command;
import core.CommandExecutable;
import utils.ConfigManager;
import utils.MessageSender;

public class CommandShowTracker extends CommandExecutable {

	public CommandShowTracker(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		MessageSender.message(command, "Tracker set inside <#" + ConfigManager.getString("trackerChannel") + ">");
		return (true);
	}
}
