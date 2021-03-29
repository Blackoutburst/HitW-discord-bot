package commands;

import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import core.Command;
import core.CommandExecutable;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.GeneralUtils;
import utils.Leaderboard;

public class CommandLeaderboard extends CommandExecutable {
	
	public CommandLeaderboard(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		char type = getType();
		int page = getPage();
		boolean discord = isDiscord();
		String fileName = discord ? "linked player" : "leaderboard";
		File index = new File(fileName);
		Canvas image = new Canvas(600, 400);
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(index);
		Leaderboard lb = new Leaderboard(lead, type);
		lb.sort();
		
		generateCanvas(image, page, lb);
		MessageSender.sendFile(command, "lead.png");
		return (true);
	}
	
	/**
	 * Generate leader board canvas
	 * @param image
	 * @param type
	 * @param page
	 * @param lead
	 */
	private void generateCanvas(Canvas image, int page, Leaderboard lb) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int y = 0;
		
		image.drawBackground();
		image.drawStringCenter(lb.getCanvasName(), 300, 40, 32, Color.white);

		for (int i = (10 * page); i < (10 * page) + 10; i++) {
			if (i > lb.getPlayers().size() || i + 10 > lb.getPlayers().size()) break;
			LeaderboardPlayer player = lb.getPlayers().get(i);
			String str = "#" + (i+1) + " " + player.name + " - " + formatter.format(lb.getPlayerStat(i));
			image.drawStringCenter(str, 300, 75+(35*y), 26, Color.white);
			y++;
		}
		image.save("lead.png");
	}
	
	/**
	 * Check is the leader board is discord only
	 * @return
	 */
	private boolean isDiscord() {
		for (String arg : command.getArgs()) {
			if (arg.equalsIgnoreCase("discord")) {
				return (true);
			}
		}
		return (false);
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
		return ('w');
	}
	
	/**
	 * Get leader board page
	 * @return
	 */
	private int getPage() {
		int page = 1;
		
		for (String arg : command.getArgs()) {
			try {
				page = Integer.valueOf(arg);
				break;
			} catch(Exception e) {}
		}
		if (page <= 0) page = 1;
		page--;
		return (page);
	}
}
