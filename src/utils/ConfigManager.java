package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class ConfigManager {
	
	private static JSONObject obj = null;
	
	/**
	 * Create new JSON object from configuration file
	 * @param file
	 * @throws IOException
	 */
	public void init(String file) throws IOException {
		String json = new String(Files.readAllBytes(Paths.get(file)));
		obj = new JSONObject(json);
	}
	
	/**
	 * Get string value
	 * @param string
	 * @return
	 */
	public static String getString(String string) {
		return (obj.getString(string));
	}
	
	/**
	 * Get role id
	 * @param role
	 * @return
	 */
	public static String getRoleId(String role) {
		return (obj.getJSONObject("roles").getString(role));
	}
	
	/**
	 * Get message
	 * @param role
	 * @return
	 */
	public static String getMessage(String message) {
		return (obj.getJSONObject("message").getString(message));
	}
	
	/**
	 * Set string value
	 * @param string
	 * @param value
	 */
	public static void setString(String string, String value) {
		obj.put(string, value);

		try {
			Files.write(Paths.get("config.json"), obj.toString(4).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
