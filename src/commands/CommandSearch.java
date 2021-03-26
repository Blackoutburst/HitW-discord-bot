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
import utils.Canvas;
import utils.LeaderboardPlayer;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;

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
		
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("leaderboard"));
		lead = sort(type, lead);
		
		if (pos >= lead.size()) return (outOfBounds(this));
		
		LeaderboardPlayer player = lead.get(pos);
		String data = Request.getPlayerStatsUUID(player.uuid);
		
		Canvas image = new Canvas(600, 400);
		
		image.drawCustomBackground(GeneralUtils.getCustomBackground(player.name), 0, 0, 600, 400);
		image.drawStringCenter(Stats.getSubTitle(player.name), 300, 70, 26, Color.white);
	
		GeneralUtils.createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		return (true);
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
