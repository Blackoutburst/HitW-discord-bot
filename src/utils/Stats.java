package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONObject;

public class Stats {
	
	/**
	 * Return player discord id
	 * @param json
	 * @return
	 */
	public static String getDiscordId(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("discordid"));
	}
	
	/**
	 * Return player UUID
	 * @param json
	 * @return
	 */
	public static String getUUID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("uuid"));
	}
	
	
	/**
	 * Return player UUID
	 * @param json
	 * @return
	 */
	public static String getID(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("id"));
	}
	
	
	/**
	 * Return player subtitle
	 * @param player
	 * @return
	 */
	public static String getSubTitle(String uuid) {
		JSONObject obj = Utils.readJson("linked player/" + uuid + "/data.json");
		return (obj.getString("subtitle"));
	}
	
	/**
	 * Return player name
	 * @param json
	 * @return
	 */
	public static String getName(String json) {
		JSONObject obj = new JSONObject(json);
		
		return (obj.getString("name"));
	}
	
	/**
	 * Return player wins
	 * @param json
	 * @return
	 */
	public static String getWins(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value =  obj.getInt("wins");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player walls cleared
	 * @param json
	 * @return
	 */
	public static String getWalls(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("walls");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player qualification score
	 * @param json
	 * @return
	 */
	public static String getQualification(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("qualification");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player final score
	 * @param json
	 * @return
	 */
	public static String getFinals(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int value = 0;
		
		try {
			value = obj.getInt("finals");
		} catch (Exception e) {}
		return (formatter.format(value));
	}
	
	/**
	 * Return player total score
	 * @param json
	 * @return
	 */
	public static String getTotal(String json) {
		JSONObject obj = new JSONObject(json);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int q = 0;
		int f = 0;
		
		try {
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
		} catch (Exception e) {}
		
		return (formatter.format(q + f));
	}
	
	/**
	 * Return player wins
	 * @param json
	 * @return
	 */
	public static int getWinsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value =  obj.getInt("wins");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player walls cleared
	 * @param json
	 * @return
	 */
	public static int getWallsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("walls");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player qualification score
	 * @param json
	 * @return
	 */
	public static int getQualificationToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("qualification");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player final score
	 * @param json
	 * @return
	 */
	public static int getFinalsToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int value = 0;
		
		try {
			value = obj.getInt("finals");
		} catch (Exception e) {}
		return (value);
	}
	
	/**
	 * Return player total score
	 * @param json
	 * @return
	 */
	public static int getTotalToInt(String json) {
		JSONObject obj = new JSONObject(json);
		int q = 0;
		int f = 0;
		
		try {
			q = obj.getInt("qualification");
			f = obj.getInt("finals");
		} catch (Exception e) {}
		
		return (q + f);
	}
	
}
