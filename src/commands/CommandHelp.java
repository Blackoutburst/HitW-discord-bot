package commands;

import core.Command;
import core.CommandExecutable;
import net.dv8tion.jda.api.EmbedBuilder;

public class CommandHelp extends CommandExecutable {

	public CommandHelp(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (senderIsStaff()) {
			sendStaffHelp();
		}
		sendHelp();
		return (true);
	}

	private void sendStaffHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/d786e98fbc429896918244a20e20b9d1.png";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wally help", wallyURL, iconURL)
			.setTitle("Staff commands")
			.setColor(0x00d492)
			.addField("!getconfig", "Get Wally current configuration file", false)
			.addField("!setconfig (file required)","Update Wally configuration file" ,false)
			.addField("!settracker", "Set tracker in the current channel", false)
			.addField("!showtracker", "Show where the tracker is currently set", false)
			.addField("!say [message]", "Talk through wally", false)
			.addField("!link [discord ID] [IGN]", "Link a player Discord and Minecraft account", false)
			.addField("!unlink [discordID/IGN]", "Unlink a player Discord and Minecraft account", false)
			.addField("!removebackground [IGN]", "Remove a user stats background", false)
			.addField("!forcePB [IGN] [Q/F]", "Display a player personal best (no arguments mean both pb)", false)
			.addField("!forcetracker", "Force the tracker to check everyone (should not be activated all time)", false)
			.addField("!setchangelog (file required)", "Change Wally changelog", false)
			.addField("!getchangelog", "Get wally Changelog", false)
			.addField("!changelog", "Show latest Wally modifications", false)
			.addField("!poll [message]", "Make a poll", false);
		
		command.getEvent().getChannel().sendMessage(embed.build()).complete();
	}

	private void sendHelp() {
		String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/d786e98fbc429896918244a20e20b9d1.png";
		String wallyURL = "https://github.com/Blackoutburst/Wally";
		EmbedBuilder embed = new EmbedBuilder()
			.setAuthor("Wally help", wallyURL, iconURL)
			.setTitle("Available commands")
			.setColor(0x00d492)
			.addField("!help/!h", "Show this message", false)
			.addField("!pack","Redirect you to the #resource-packs channel" ,false)
			.addField("!stats/!stat/!s [player]", "Show player Hole in the Wall stats", false)
			.addField("!ping", "Show how much Wally is dying", false)
			.addField("!compare/!c [player1] [player2]", "Compare two players Hole in the Wall stats", false)
			.addField("!setbackground/!setbg (file required)", "Change your stats background", false)
			.addField("!resetbackground/!rbg", "Reset your stats background", false)
			.addField("!leaderboard/!lead/!lb [W/R/Q/F/T] [page] [id]", "Show Hole in the Wall leaderboard", false)
			.addField("!convert [Q/F] [score]", "Convert a finals score to a qualification score and the other way around", false)
			.addField("!search [W/R/Q/F/T] [position]", "Search a player stats from their leaderboard position", false)
			.addField("!whois/!who [IGN]", "Tell information about a player", false);
		
		command.getEvent().getChannel().sendMessage(embed.build()).complete();
	}
}
