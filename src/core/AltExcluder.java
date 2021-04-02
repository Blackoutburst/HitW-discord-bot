package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AltExcluder {
	
	/**
	 * Check is this account is an alt using their uuid
	 * @param uuid
	 * @return
	 */
	public static boolean isAnAlt(String uuid) {
		List<String> altUuid = readList();
		
		for (String str : altUuid) {
			if (uuid.equals(str)) {
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
