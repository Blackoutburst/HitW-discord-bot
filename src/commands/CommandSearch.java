package commands;

import java.awt.Color;
import java.io.File;
import java.util.List;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;
import utils.Leaderboard;

public class CommandSearch extends CommandExecutable {
	
	public CommandSearch(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 2) return (badUsage(this));
		
		char type = getType();
		int pos = getPosition();
		
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("leaderboard"));
		Leaderboard lb = new Leaderboard(lead, type);
		lb.sort();
		
		if (pos >= lb.getPlayers().size()) return (outOfBounds(this));
		
		LeaderboardPlayer player = lb.getPlayers().get(pos);
		String data = Request.getPlayerStatsUUID(player.uuid);
		
		Canvas image = new Canvas(600, 400);

		
		image.drawCustomBackground(GeneralUtils.getCustomBackground(player.uuid), 0, 0, 600, 400);
		image.drawStringCenter(Stats.getSubTitle(player.uuid), 300, 70, 26, Color.white);
	
		GeneralUtils.createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		return (true);
	}
	
	/**
	 * Get leader board type
	 * @return
	 */
	private char getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1) {
				return (arg.toLowerCase().charAt(0));
			}
		}
		return 'w';
	}
	
	/**
	 * Get leader board page
	 * @return
	 */
	private int getPosition() {
		int pos = 1;
		
		for (String arg : command.getArgs()) {
			try {
				pos = Integer.valueOf(arg);
				break;
			} catch(Exception e) {}
		}
		if (pos <= 0) pos = 1;
		pos--;
		return (pos);
	}
}
