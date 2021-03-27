package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import java.awt.Color;

import comparators.PlayerComparatorF;
import comparators.PlayerComparatorQ;
import comparators.PlayerComparatorRounds;
import comparators.PlayerComparatorTotal;
import comparators.PlayerComparatorWins;
import core.Bot;
import core.Command;
import core.Request;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class GeneralUtils {

	/**
	 * Check if member is staff
	 * @param member
	 * @return
	 */
	public static boolean isStaff(Member member) {
		Role staffRole = Bot.server.getRoleById(ConfigManager.getRoleId("Staff"));
		List<Role> memberRoles = member.getRoles();
		return (memberRoles.contains(staffRole) || member.hasPermission(Permission.ADMINISTRATOR));
	}

	/**
	 * Remove useless space in string
	 * @param str
	 * @return
	 */
	public static String removeDuplicateSpace(String str) {
		return str.replaceAll("\\s+", " ");
	}

	/**
	 * Check if the player is linked
	 * @param player
	 * @return
	 */
	public static boolean isLinkedIGN(String player) {
		String uuid = Request.getPlayerUUID(player);
		String playerFolder = "linked player/" + uuid;

		return new File(playerFolder).exists();
	}

	/**
	 * Check if the player is linked
	 * @param uuid
	 * @return
	 */
	public static boolean isLinkedUUID(String uuid) {
		String playerFolder = "linked player/" + uuid;

		return new File(playerFolder).exists();
	}
	
	/**
	 * Check if the player is linked
	 * @param player
	 * @return
	 */
	public static boolean isLinkedDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("discordid").equals(discordid)) {
				return (true);
			}

		}
		return (false);
	}

	/**
	 * Get player uuid from is discord id
	 * @param player
	 * @return
	 */
	public static String getUUIDfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("discordid").equals(discordid)) {
				return (obj.getString("uuid"));
			}
		}
		return (null);
	}

	/**
	 * Get user IGN from his discord account
	 * @param player
	 * @return
	 */
	public static String getIGNfromDiscord(String discordid) {
		File index = new File("linked player");
		String[] entries  = index.list();

		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("discordid").equals(discordid)) {
				return (obj.getString("name"));
			}
		}
		return (null);
	}

	/**
	 * Get user discord id from is IGN
	 * @param player
	 * @return
	 */
	public static String getDiscordfromIGN(String ign) {
		File index = new File("linked player");
		String[] entries  = index.list();

		for (String s : entries) {
			File playerFolder = new File(index.getPath(), s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");

			if (obj.getString("name").equalsIgnoreCase(ign)) {
				return (obj.getString("discordid"));
			}
		}
		return (null);
	}

	/**
	 * Get player background path
	 * @param player
	 * @return
	 */
	public static String getCustomBackground(String uuid) {
		String playerFolder = "linked player/" + uuid;

		if(new File(playerFolder + "/background.png").exists()) {
			return (playerFolder + "/background.png");
		}

		return ("res/background.png");
	}

	/**
	 * Create an new JSON object from a json file
	 * @param file
	 * @return
	 */
	public static JSONObject readJson(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			String line = "";

			while ((line = reader.readLine()) != null) {
				json += line;
			}
			reader.close();
			return (new JSONObject(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (null);
	}

	/**
	 * Create an new string from a json file
	 * @param file
	 * @return
	 */
	public static String readJsonToString(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			String line = "";

			while ((line = reader.readLine()) != null) {
				json += line;
			}
			reader.close();
			return (json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (null);
	}

	/**
	 * Add a player to the leader board
	 * @param uuid
	 * @param data
	 */
	public static void addToLeaderBoard(String uuid, String data, Command command) {
		if (uuid.equals("9c05f51a1d644dc4b2ad3f4cff85a64b")) return;
		if (!new File("leaderboard/" + uuid).exists()) {
			new File("leaderboard/" + uuid).mkdir();
			JSONObject obj = new JSONObject()
				.put("name", API.getName(data))
				.put("wins", API.getWins(data))
				.put("subtitle", "")
				.put("walls", API.getWalls(data))
				.put("qualification", API.getQualification(data))
				.put("finals", API.getFinals(data))
				.put("uuid", uuid);

			try {
				PrintWriter writer = new PrintWriter("leaderboard/" + uuid + "/data.json");
				writer.write(obj.toString(4));
				writer.close();
				MessageSender.messageJSON(command, "leaderboard add");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get gender prefix from roles
	 * @param discordid
	 * @return
	 */
	public static String getGenderPrefix(String discordid) {
		Role hisRole = Bot.server.getRoleById(ConfigManager.getRoleId("He/Him"));
		Role herRole = Bot.server.getRoleById(ConfigManager.getRoleId("She/Her"));
		Role theirRole = Bot.server.getRoleById(ConfigManager.getRoleId("They/Them"));

		List<Role> memberRoles = Bot.server.getMemberById(discordid).getRoles();

		boolean his = memberRoles.contains(hisRole);
		boolean her = memberRoles.contains(herRole);
		boolean their = memberRoles.contains(theirRole);

		if (his && !her && !their) return ("his");
		if (her && !his && !their) return ("her");
		return ("their");
	}

	/**
	 * Check is the user in inside the discord server
	 * @param discordid
	 * @return
	 */
	public static boolean isInsideTheServer(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null);
	}

	/**
	 * Check if the user in online
	 * @param discordid
	 * @return
	 */
	public static boolean isOnline(String discordid) {
		Member member = Bot.server.getMemberById(discordid);

		return (member != null && (member.getOnlineStatus() != OnlineStatus.OFFLINE || member.getOnlineStatus() != OnlineStatus.IDLE));
	}

	/**
	 * Remove a member from the database
	 * @param uuid
	 */
	public static void unlinkMember(String uuid) {
		File index = new File("linked player/" + uuid);
		String[] entries = index.list();

		for(String s : entries) {
			File file = new File(index.getPath(), s);

			file.delete();
		}
		index.delete();
	}

	/**
	 * Update player data file
	 * @param data
	 * @param uuid
	 * @param folder
	 */
	public static void updateFile(String data, String localData, String uuid, String folder) {
		JSONObject obj = new JSONObject(localData)
			.put("wins", API.getWinsToInt(data))
			.put("walls", API.getWallsToInt(data))
			.put("qualification", API.getQualificationToInt(data))
			.put("finals", API.getFinalsToInt(data))
			.put("name", API.getName(data));

		try {
			PrintWriter writer = new PrintWriter(folder + "/" + uuid + "/data.json");
			writer.write(obj.toString(4));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort leader board
	 * @param user
	 * @return
	 */
	public static List<LeaderboardPlayer> sortLB(List<LeaderboardPlayer> lead, char type) {
		switch (type) {
			case 'w' : Collections.sort(lead, new PlayerComparatorWins()); break;
			case 'r' : Collections.sort(lead, new PlayerComparatorRounds()); break;
			case 'q' : Collections.sort(lead, new PlayerComparatorQ()); break;
			case 'f' : Collections.sort(lead, new PlayerComparatorF()); break;
			case 't' : Collections.sort(lead, new PlayerComparatorTotal()); break;
			default : Collections.sort(lead, new PlayerComparatorWins()); break;
		}
		return (lead);
	}

	/**
	 * Get player position in the leader board
	 * @param user
	 * @return
	 */
	public static String getLBPos(String user, char type) {
		int pos = getLBPosToInt(user, type) + 1;
		return (pos != 10001) ? " (#"+ pos + ")" : "";
	}

	/**
	 * Get player position in the leader board
	 * @param user
	 * @return
	 */
	public static int getLBPosToInt(String user, char type) {
		List<LeaderboardPlayer> lead = generatePlayerList(new File("leaderboard"));
		lead = sortLB(lead, type);

		for(int i = 0; i < lead.size(); i++) {
			if(lead.get(i).name.equals(user)) return i;
		}
		return (10000);
	}

	/**
	 * Generate list of player from specified data folder
	 * @param index
	 * @return
	 */
	public static List<LeaderboardPlayer> generatePlayerList(File index) {
		List<LeaderboardPlayer> lead = new ArrayList<LeaderboardPlayer>();

		String[]entries = index.list();
		for(String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			String data = GeneralUtils.readJsonToString(playerFolder + "/data.json");

			lead.add(new LeaderboardPlayer(data));
		}
		return (lead);
	}

	/**
	 * Create stats image
	 * @param image
	 * @param data
	 */
	public static void createImage(Canvas image, String data) {
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
		
		image.drawStringLeft("Wins: " + API.getWins(data) + GeneralUtils.getLBPos(API.getName(data), 'w'), 150, 125, 24, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + GeneralUtils.getLBPos(API.getName(data), 'r'), 150, 175, 24, Color.white);
		image.drawStringLeft("Best qualification score: " + API.getQualification(data) + GeneralUtils.getLBPos(API.getName(data), 'q'), 150, 225, 24, Color.white);
		image.drawStringLeft("Best final score: " + API.getFinals(data) + GeneralUtils.getLBPos(API.getName(data), 'f'), 150, 275, 24, Color.white);
		image.drawStringLeft("Q/F total: " + API.getTotal(data) + GeneralUtils.getLBPos(API.getName(data), 't'), 150, 325, 24, Color.white);
		image.save("stats.png");
	}
}
