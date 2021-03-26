package commands;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.MessageSender;
import utils.GeneralUtils;

public class CommandUnlink extends CommandExecutable {

	public CommandUnlink(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		String uuid = GeneralUtils.getUUIDfromDiscord(command.getArgs()[0]);
		if (uuid == null) uuid = Request.getPlayerUUID(command.getArgs()[0]);
		if (uuid == null) return (unlinkError(this));
		
		GeneralUtils.unlinkMember(uuid);
		
		MessageSender.messageJSON(command, "unlink");
		return (true);
	}
}
