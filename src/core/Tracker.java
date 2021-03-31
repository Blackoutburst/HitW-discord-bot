package core;

import java.io.File;

import utils.API;
import utils.MessageSender;
import utils.Stats;
import utils.GeneralUtils;

public class Tracker {
	
	public static boolean forced = false;
	public static Thread trackerThread = null;
	
	public void start() {
		trackerThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					checkUsers();
				}
			}
		});
		trackerThread.setDaemon(true);
		trackerThread.setName("Tracker");
		trackerThread.start();
	}
	
	/**
	 * Check every linked user for new personal best
	 */
	private void checkUsers() {
		File index = new File("linked player");
		String[] entries = index.list();
		
		for(String s: entries) {
			File playerFolder = new File(index.getPath(), s);
			String data = "";
			String fileLocation = playerFolder + "/data.json";

			// make sure player data exists before attempting to read from it
			File tmpFile = new File(fileLocation);
			if(tmpFile.exists()) continue;

			String localData = GeneralUtils.readJsonToString(fileLocation);
			String discordid = Stats.getDiscordId(localData);

			String uuid = Stats.getUUID(localData);
			int oldQ = 0;
			int newQ = 0;
			int oldF = 0;
			int newF = 0;
			
			if(!GeneralUtils.isInsideTheServer(discordid)) GeneralUtils.unlinkMember(uuid);
			if(!GeneralUtils.isOnline(discordid) && !forced) continue;

			data = Request.getPlayerStatsUUID(uuid);
			if (data == null) continue;
			if (API.getPlayer(data) == null) continue;

			oldQ = Stats.getQualificationToInt(localData);
			newQ = API.getQualificationToInt(data);
			oldF = Stats.getFinalsToInt(localData);
			newF = API.getFinalsToInt(data);

			GeneralUtils.updateFile(data, localData, uuid, "linked player");
			GeneralUtils.updateFile(data, localData, uuid, "leaderboard");
			
			if (newQ > oldQ) MessageSender.pbMessage(data, discordid, uuid, 'q');
			if (newF > oldF) MessageSender.pbMessage(data, discordid, uuid, 'f');
			// Wait 500ms before making more api requests to avoid rate limit
			delay(500);
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
