package main;

import java.io.File;
import javax.security.auth.login.LoginException;

import core.Bot;

public class Main {
	private static final String TOKEN = "X";
	private static final String ACTIVITY = "!help";
	public static final String bypassID = "X";
	public static final String serverID = "X";
	
	/**
	 *  Main
	 * @param args
	 * @author Blackoutburst
	 */
	public static void main(String[] args) {
		Bot bot = new Bot();

		setup();
		
		try {
			bot.login(TOKEN, ACTIVITY);
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate usefull folder on start
	 * @author Blackoutburst
	 */
	private static void setup() {
		File f = new File("linked player");
		
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File("leaderboard");
		
		if (!f.exists()) {
			f.mkdir();
		}
	}
}
