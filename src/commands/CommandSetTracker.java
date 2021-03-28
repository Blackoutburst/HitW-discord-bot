package commands;

import core.Command;
import core.CommandExecutable;
import utils.ConfigManager;
import utils.MessageSender;

public class CommandSetTracker extends CommandExecutable {

	public CommandSetTracker(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		ConfigManager.setString("trackerChannel", command.getEvent().getChannel().getId());
		MessageSender.message(command, "Tracker set to <#" + command.getEvent().getChannel().getId() + ">");
		return (true);
	}
}
