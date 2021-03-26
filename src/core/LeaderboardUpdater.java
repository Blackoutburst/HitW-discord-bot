package core;

import java.io.File;
import java.util.List;

import net.dv8tion.jda.api.entities.Member;
import utils.API;
import utils.LeaderboardPlayer;
import utils.GeneralUtils;

public class LeaderboardUpdater {
	
	public static boolean forced = false;
	
	public LeaderboardUpdater() {
		Thread lbupdaterThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					checkUsers();
					updateLifeTimeRoles();
				}
			}
		});
		lbupdaterThread.setDaemon(true);
		lbupdaterThread.setName("Leaderboard updater");
		lbupdaterThread.start();
	}
	
	/**
	 * User player leaderboard role
	 */
	private void updateLifeTimeRoles() {
		List<LeaderboardPlayer> lead = GeneralUtils.generatePlayerList(new File("leaderboard"));
	
		for (LeaderboardPlayer player : lead) {
			if(GeneralUtils.isLinkedIGN(player.name)) {
				Member member = Bot.server.getMemberById(player.discord);
				
				new RolesManager().cleanLifeTimeRole(member);
				if (GeneralUtils.getLBPosToInt(player.name, 'w') <= 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Wins");
				if (GeneralUtils.getLBPosToInt(player.name, 'q') <= 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Q");
				if (GeneralUtils.getLBPosToInt(player.name, 'f') <= 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime F");
				if (GeneralUtils.getLBPosToInt(player.name, 'r') <= 10) new RolesManager().addLifeTimeRole(member, "Top 10 Lifetime Walls");
			}
		}
	}
	
	/**
	 * Check every user register and update them
	 */
	private void checkUsers() {
		File index = new File("leaderboard");
		String[] entries = index.list();
		
		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String uuid = playerFolder.getName();
			String data = Request.getPlayerStatsUUID(playerFolder.getName());
			String localData = GeneralUtils.readJsonToString(playerFolder + "/data.json");
			
			if (data == null) continue;
			if (API.getPlayer(data) == null) continue;
			
			GeneralUtils.updateFile(data, localData, uuid, "leaderboard");
			delay(60000);
		}
	}
	
	/**
	 * Stop the current thread for a defined amount of time
	 * @param ms
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
