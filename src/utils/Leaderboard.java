package utils;

import java.util.Collections;
import java.util.List;

import comparators.*;

public class Leaderboard {
	
	enum Type {
		W,
		R,
		Q,
		F,
		T
	}

	private List<LeaderboardPlayer> players;
	private Type type;

	public Leaderboard(List<LeaderboardPlayer> players, char type) {
		this.players = players;
		this.type = getTypeFromChar(type);
	}

	public void sort() {
		switch (type) {
			case W : Collections.sort(this.players, new PlayerComparatorWins()); break;
			case R : Collections.sort(this.players, new PlayerComparatorRounds()); break;
			case Q : Collections.sort(this.players, new PlayerComparatorQ()); break;
			case F : Collections.sort(this.players, new PlayerComparatorF()); break;
			case T : Collections.sort(this.players, new PlayerComparatorTotal()); break;
			default : Collections.sort(this.players, new PlayerComparatorWins()); break;
		}
	}

	public int getPlayerStat(int index) {
		LeaderboardPlayer player = players.get(index);
		switch (this.type) {
			case W : return (player.wins);
			case R : return (player.walls);
			case Q : return (player.qualification);
			case F : return (player.finals);
			case T : return (player.total);
			default: return (player.wins);
		}
	}

	/**
	 * Get canvas name from leader board type
	 * @param type
	 * @return
	 */
	public String getCanvasName() {
		switch (this.type) {
			case W: return ("Wins Leaderboard");
			case R: return ("Walls cleared Leaderboard");
			case Q: return ("Qualification Leaderboard");
			case F: return ("Finals Leaderboard");
			case T: return ("Q/F Total Leaderboard");
			default: return ("Wins Leaderboard");
		}
	}

	public List<LeaderboardPlayer> getPlayers() {
		return players;
	}

	private static Type getTypeFromChar(char c) {
		switch(c) {
			case 'w' : return (Type.W);
			case 'r' : return (Type.R);
			case 'q' : return (Type.Q);
			case 'f' : return (Type.F);
			case 't' : return (Type.T);
			default : return (Type.W);
		}
	}
}
