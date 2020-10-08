package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Request {
	/**
	 * Run JS file to get player information
	 * @param user
	 * @return player information
	 * @see player_request.js
	 * @author Blackoutburst
	 */
	public static String getPlayerInfo(String user) {
		Bot.request++;
		if (Bot.request >= 119) {
			return "API LIMITATION";
		}
		ProcessBuilder pb = new ProcessBuilder("node", "player_request.js", user);
		
		try {
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
			   builder.append(line);
			   builder.append(System.getProperty("line.separator"));
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/**
	 * Use username to get user uuid (JS)
	 * @param user
	 * @return user UUID
	 * @see request_uuid.js
	 * @author Blackoutburst
	 */
	public static String getPlayerUUID(String user) {
		ProcessBuilder pb = new ProcessBuilder("node", "request_uuid.js", user);
		
		try {
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ( (line = reader.readLine()) != null) {
			   builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
