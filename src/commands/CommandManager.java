package commands;

import core.Command;

public class CommandManager {
	
	private static final boolean ADMIN = true;
	private static final boolean EVERYONE = false;
	
	public CommandManager(Command command) {
		switch (command.getName()) {
			case "help": case "h": new CommandHelp(command, EVERYONE, "!help").run(); break;
			case "pack": new CommandPack(command, EVERYONE, "!pack").run(); break;
			case "stats": case "stat": case "s": new CommandStats(command, EVERYONE, "!stats [player]").run(); break;
			case "ping": new CommandPing(command, EVERYONE, "!ping").run(); break;
			case "compare": case "c": new CommandCompare(command, EVERYONE, "!compare [player] [player]").run(); break;
			case "getconfig": new CommandGetConfig(command, ADMIN, "!getconfig").run(); break;
			case "setconfig": new CommandSetConfig(command, ADMIN,"!setconfig (file required)").run(); break;
			case "settracker": new CommandSetTracker(command, ADMIN, "!settracker").run(); break;
			case "showtracker": new CommandShowTracker(command, ADMIN, "!showtracker").run(); break;
			case "say": new CommandSay(command, ADMIN, "!say [message]").run(); break;
			case "link": new CommandLink(command, ADMIN, "!link [discord ID] [IGN]").run(); break;
			case "unlink": new CommandUnlink(command, ADMIN, "!unlink [discordID/IGN]").run(); break;
			case "setbackground": case "setbg": new CommandSetBackground(command, EVERYONE, "!setbackground (file required)").run(); break;
			case "resetbackground": case "rbg": new CommandResetBackground(command, EVERYONE, "!resetbackground").run(); break;
			case "removebackground": new CommandRemoveBackground(command, ADMIN, "!removebackground [IGN]").run(); break;
			case "leaderboard": case "lead": case "lb": new CommandLeaderboard(command, EVERYONE, "!leaderboard [W/R/Q/F/T] [page] [\"discord\"]").run(); break;
			case "forcepb": new CommandForcePB(command, ADMIN, "!forcePB [IGN] [Q/F]").run(); break;
			case "profile": case "p": new CommandProfile(command, EVERYONE, "!profile [player]").run(); break;
			case "forcetracker": new CommandForceTracker(command, ADMIN, "!forcetracker").run(); break;
			case "changelog": new CommandChangelog(command, EVERYONE, "!changelog").run(); break;
			case "setchangelog": new CommandSetChangelog(command, ADMIN, "!setchangelog (file required)").run(); break;
			case "getchangelog": new CommandGetChangelog(command, ADMIN, "!getchangelog").run(); break;
			case "convert": new CommandConvert(command, EVERYONE, "!convert [Q/F] [score]").run(); break;
			case "poll": new CommandPoll(command, ADMIN, "!poll [message]").run(); break;
			case "search": new CommandSearch(command, EVERYONE, "!search [W/R/Q/F/T] [position]").run(); break;
			default : return;
		}
	}
}
