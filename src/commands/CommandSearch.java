package commands;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.List;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class CommandSearch extends CommandExecutable {

	enum Type {
		W,
		R,
		Q,
		F,
		T
	}
	
	public CommandSearch(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 2) return (badUsage(this));
		
		Type type = getType();
		int pos = getPosition();
		
		List<LeaderboardPlayer> lead = Utils.generatePlayerList(new File("leaderboard"));
		lead = sort(type, lead);
		
		if (pos >= lead.size()) return (outOfBounds(this));
		
		LeaderboardPlayer player = lead.get(pos);
		String data = Request.getPlayerStatsUUID(player.uuid);
		
		Canvas image = new Canvas(600, 400);
		
		image.drawCustomBackground(Utils.getCustomBackground(player.name), 0, 0, 600, 400);
		image.drawStringCenter(Stats.getSubTitle(player.name), 300, 70, 26, Color.white);
	
		createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		return (true);
	}
	
	/**
	 * Create stats image
	 * @param image
	 * @param data
	 */
	private void createImage(Canvas image, String data) {
		image.drawImage("res/win.png", 100, 105, 24, 24);
		image.drawImage("res/wall.png", 100, 155, 24, 24);
		image.drawImage("res/q.png", 100, 205, 24, 24);
		image.drawImage("res/f.png", 100, 255, 24, 24);
		image.drawImage("res/total.png", 100, 305, 24, 24);
		
		
		if (API.getName(data).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 200, 10, 200, 53);
		} else {
			image.drawStringCenter(API.getName(data), 300, 40, 32, Color.white);
		}
		
		image.drawStringLeft("Wins: " + API.getWins(data) + Utils.getLBPos(API.getName(data), 'w'), 150, 125, 24, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + Utils.getLBPos(API.getName(data), 'r'), 150, 175, 24, Color.white);
		image.drawStringLeft("Best qualification score: " + API.getQualification(data) + Utils.getLBPos(API.getName(data), 'q'), 150, 225, 24, Color.white);
		image.drawStringLeft("Best final score: " + API.getFinals(data) + Utils.getLBPos(API.getName(data), 'f'), 150, 275, 24, Color.white);
		image.drawStringLeft("Q/F total: " + API.getTotal(data) + Utils.getLBPos(API.getName(data), 't'), 150, 325, 24, Color.white);
		image.save("stats.png");
	}
	
	/**
	 * Sort leader board
	 * @param type
	 * @param lead
	 * @return
	 */
	private List<LeaderboardPlayer> sort(Type type, List<LeaderboardPlayer> lead) {
		switch (type) {
			case W : Collections.sort(lead, new PlayerComparatorWins()); break;
			case R : Collections.sort(lead, new PlayerComparatorRounds()); break;
			case Q : Collections.sort(lead, new PlayerComparatorQ()); break;
			case F : Collections.sort(lead, new PlayerComparatorF()); break;
			case T : Collections.sort(lead, new PlayerComparatorTotal()); break;
			default : Collections.sort(lead, new PlayerComparatorWins()); break;
		}
		return (lead);
	}
	
	/**
	 * Get leader board type
	 * @return
	 */
	private Type getType() {
		for (String arg : command.getArgs()) {
			if (arg.length() == 1) {
				switch(arg.toLowerCase().charAt(0)) {
					case 'w' : return (Type.W);
					case 'r' : return (Type.R);
					case 'q' : return (Type.Q);
					case 'f' : return (Type.F);
					case 't' : return (Type.T);
					default : return (Type.W);
				}
			}
		}
		return (Type.W);
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
