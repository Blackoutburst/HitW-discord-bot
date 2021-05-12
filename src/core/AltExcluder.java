package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.API;
import utils.MessageSender;

public class AltExcluder {
	
	/**
	 * Check is this account is an alt using their uuid
	 * @param uuid
	 * @return
	 */
	public static boolean isAnAlt(String uuid, Command command) {
		List<String> altUuid = readList();
		
		for (String str : altUuid) {
			String[] split = str.split(",");
			
			if (uuid.equals(split[0])) {
				if (split.length == 1) {
					MessageSender.altAccount(command);
				} else {
					String data = Request.getPlayerStatsUUID(split[1]);
					MessageSender.altAccountOwner(command, API.getName(data));
				}
				
				return (true);
			}
		}
		
		return(false);
	}
	
	/**
	 * Read alt account list
	 * @return
	 */
	private static List<String> readList() {
		List<String> uuids = new ArrayList<String>();
		
		try {
			FileReader in = new FileReader("./alts.txt");
			BufferedReader br = new BufferedReader(in);
			String line = null;
			
			while ((line = br.readLine()) != null) {
				uuids.add(line);
			}
			br.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(uuids);
	}
}
