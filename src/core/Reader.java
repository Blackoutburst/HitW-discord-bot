package core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {
	
	/**
	 *  Get specific line argument after :
	 * @param line
	 * @return
	 */
	public static String read(int line) {
		try {
			String[] str = Files.readAllLines(Paths.get("msg_file.yml")).get(line).split(":", 2); 
			
			return str[1].substring(2, str[1].length()-1).replace("\\n", "\n");
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
