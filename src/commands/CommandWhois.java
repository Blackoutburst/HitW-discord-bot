package commands;

import java.awt.Color;

import core.Bot;
import core.Command;
import core.CommandExecutable;
import core.Request;
import net.dv8tion.jda.api.entities.Member;
import utils.API;
import utils.Canvas;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;

public class CommandWhois extends CommandExecutable {

	public CommandWhois(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {

		if (command.getArgs().length == 0) return (badUsage(this));
		
		String data = Request.getPlayerStats(command.getArgs()[0]);
		if (data == null) return (unknownPlayer(this, command.getArgs()[0]));
		if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[0]));
		if (API.getUUID(data).equals("9293868b414c42b2bd8e3bcb791247b9")) return (unknownPlayer(this, command.getArgs()[0]));
		String discordId = GeneralUtils.getDiscordfromIGN(API.getName(data));
		String discordNick;
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			discordNick = (member.getNickname() != null) ? member.getNickname() : member.getEffectiveName(); 
		} else {
			discordNick = "N/A";
		}
		discordNick = sanitizeName(discordNick);
		String wins = API.getWins(data);
		String qual = API.getQualification(data);
		String finals = API.getFinals(data);
		String uuid = API.getUUID(data);

		String format = "**IGN** : %s\n**Discord** : %s\n**Wins** : %s\n**Qualifiers** : %s\n**Finals** : %s\n**UUID** : %s";
		command.getEvent().getChannel().sendMessageFormat(format, command.getArgs()[0], discordNick, wins, qual, finals, uuid).complete();
		
		return (true);
	}

	private String sanitizeName(String name) {
		return name.replaceAll("@", "@ ");
	}
}
