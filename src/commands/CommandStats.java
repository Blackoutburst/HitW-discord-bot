package commands;

import java.awt.Color;

import core.AltExcluder;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;

public class CommandStats extends CommandExecutable {

	public CommandStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		
		String data = null;
		String uuid = null;
		
		if (command.getArgs().length == 0) {
			if (GeneralUtils.isLinkedDiscord(command.getSender().getId())) {
				data = Request.getPlayerStatsUUID(GeneralUtils.getUUIDfromDiscord(command.getSender().getId()));
				uuid = GeneralUtils.getUUIDfromDiscord(command.getSender().getId());
			} else {
				return (badUsage(this));
			}
		} else {
			data = Request.getPlayerStats(command.getArgs()[0]);
			if (data == null) return (unknownPlayer(this, command.getArgs()[0]));
			if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[0]));
			uuid = API.getUUID(data);
		}
		
		Canvas image = new Canvas(600, 400);
		
		if (command.getArgs().length == 0) {
			image.drawCustomBackground(GeneralUtils.getCustomBackground(uuid), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(uuid), 300, 70, 26, Color.white);
		} else {
			image.drawCustomBackground(GeneralUtils.getCustomBackground(uuid), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(uuid), 300, 70, 26, Color.white);
		}
		
		GeneralUtils.createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		
		String fileLocation = "leaderboard/" + uuid + "/data.json";
		String localData = GeneralUtils.readJsonToString(fileLocation);
		GeneralUtils.updateFile(data, localData, uuid, "leaderboard");

		if(!AltExcluder.isAnAlt(uuid, this.command) && API.getWinsToInt(data) >= 25) {
			GeneralUtils.addToLeaderBoard(uuid, data, command);
		}
		return (true);
	}
}
