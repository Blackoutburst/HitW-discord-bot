package commands;

import java.awt.Color;

import core.Bot;
import core.Command;
import core.CommandExecutable;
import core.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import utils.API;
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
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator(); 
		}
		discordNick = sanitizeName(discordNick);
		String uuid = API.getUUID(data);
		String IGN = API.getName(data);

		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor("Information about : "+IGN, "https://crafatar.com/avatars/" + uuid + "?overlay", "https://crafatar.com/avatars/" + uuid + "?overlay");
		embed.setTitle(IGN + " NameMC profile", "https://fr.namemc.com/profile/"+uuid);
		embed.setColor(new Color(0, 128, 255));
		embed.addField("Discord", discordNick, false);
		embed.addField("UUID", uuid, false);
		command.getEvent().getChannel().sendMessage(embed.build()).complete();
		
		return (true);
	}

	/**
	 * Get discord name
	 * @param name
	 * @return
	 */
	private String sanitizeName(String name) {
		return name.replaceAll("@", "@ ");
	}
}
