package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

public class Tracker {

	public Guild server;

	/**
	 * Track user pb displays them in chat and change user role
	 * @author Blackoutburst
	 */
	public void start() throws IOException {
		Thread trackerThread = new Thread(new Runnable(){
			public void run(){
				while (true) {
					String output = "";
					String[] value;
					File index = new File("linked player");
					String[]entries = index.list();
					String channelID = readValue("tracker");
					for(String s: entries) {
						int role_level = 0;
						String user = "";
						String discord = "";
						String qualification = "0";
						String finals = "0";
						String wins = "0";
						String rounds = "0";
						String currentQualification = "0";
						String currentFinals = "0";
						File f = new File(index.getPath(),s);
						
						
						discord = readValue(f+"/discord");
						 try {
							if (server.getMemberById(discord).getOnlineStatus() == OnlineStatus.OFFLINE || server.getMemberById(discord).getOnlineStatus() == OnlineStatus.IDLE) {
								continue;
							}
						 } catch (Exception e) {
							 continue;
						 }
						 
						 role_level = getRoleLevel(discord);
						if (role_level == -1) {
							continue;
						}
						
						output = Request.getPlayerInfoUUID(f.getName());
						if (output.equals("API LIMITATION")) {
							System.out.println("Tracker aborted due to api limiation");
							break;
						}
						value = output.split("\n");
						for (int i = 0; i < value.length; i++) {
							if (value[i].contains("hitw_record_q")) {
								qualification = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("hitw_record_f")) {
								finals = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("wins_hole_in_the_wall")) {
								wins = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("rounds_hole_in_the_wall")) {
								rounds = value[i].replaceAll("[^0-9]", "");
							}
							if (value[i].contains("displayname")) {
								user = value[i].replace(" ", "").replace("\'", "").replace(",", "").split(":")[1];
							}
						}
						currentQualification = readValue(f+"/Q");
						currentFinals = readValue(f+"/F");
						onHighscore(currentQualification, qualification, currentFinals, finals, role_level, channelID, discord, f, user);
						delay(700);
						
						try {
							PrintWriter writer = new PrintWriter(f+"/W");
							writer.write(String.valueOf(wins));
							writer.close();
							writer = new PrintWriter(f+"/R");
							writer.write(String.valueOf(rounds));
							writer.close();
							writer = new PrintWriter(f+"/name");
							writer.write(String.valueOf(user));
							writer.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		});
		trackerThread.setDaemon(true);
		trackerThread.setName("Tracker");
		trackerThread.start();
	}
	
	/**
	 * Perform action on new user highscore
	 * @param currentQualification
	 * @param qualification
	 * @param currentFinals
	 * @param finals
	 * @param role_level
	 * @param channelID
	 * @param discord
	 * @param f
	 * @param user
	 * @author Blackoutburst
	 */
	private void onHighscore(String currentQualification, String qualification, String currentFinals, String finals,
			int role_level, String channelID, String discord, File f, String user) {
		if (Integer.valueOf(currentQualification) < Integer.valueOf(qualification)) {
			server.getTextChannelById(channelID).sendMessage(Reader.read(Lines.qualifiers_score).replace("%player%", user.replace("_", "\\_")).replace("%score%", qualification)).complete();
			writeHighScore(Integer.valueOf(qualification), f, "Q");
			setRole(discord, Integer.valueOf(qualification), role_level);
		}
		if (Integer.valueOf(currentFinals) < Integer.valueOf(finals)) {
			server.getTextChannelById(channelID).sendMessage(Reader.read(Lines.finals_score).replace("%player%", user.replace("_", "\\_")).replace("%score%", finals)).complete();
			writeHighScore(Integer.valueOf(finals), f, "F");
			setRole(discord, Integer.valueOf(finals), role_level);
		}
	}
	
	/**
	 * Add delay to tracker
	 * @param ms
	 * @author Blackoutburst
	 */
	private void delay(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read file value
	 * @param file
	 * @return value read
	 * @author Blackoutburst
	 */
	private String readValue(String file) {
		String str = "";
		try {
			str = Files.readAllLines(Paths.get(file)).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * Write new highscore
	 * @param score
	 * @param f
	 * @param type
	 * @author Blackoutburst
	 */
	private void writeHighScore(int score, File f, String type) {
		try {
			PrintWriter writer = new PrintWriter(f+"/"+type);
			writer.write(String.valueOf(score));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return user role level
	 * @param discord
	 * @return role level
	 * @author Blackoutburst
	 */
	private int getRoleLevel(String discord) {
		int role_level = 0;
		List<Role> roles = null;
		try {
			roles = server.getMemberById(discord).getRoles();
		} catch(Exception e) {
			return -1;
		}
		
		
		for (Role r : roles) {
			if (r.getName().equals("50+ Club") && role_level <= 0) {role_level = 1;}
			if (r.getName().equals("100+ Club") && role_level <= 1) {role_level = 2;}
			if (r.getName().equals("150+ Club") && role_level <= 2) {role_level = 3;}
			if (r.getName().equals("200+ Club") && role_level <= 3) {role_level = 4;}
			if (r.getName().equals("250+ Club") && role_level <= 4) {role_level = 5;}
			if (r.getName().equals("300+ Club") && role_level <= 5) {role_level = 6;}
			if (r.getName().equals("350+ Club") && role_level <= 6) {role_level = 7;}
		}
		return role_level;
	}
	
	/**
	 * Set user new role and remove all lower one
	 * @param discord
	 * @param newScore
	 * @param role_level
	 * @author Blackoutburst
	 */
	private void setRole(String discord, int newScore, int role_level) {
		List<Role> roles = server.getMemberById(discord).getRoles();
		if (Integer.valueOf(newScore) > (Integer.valueOf(role_level)) * 50) {
			for (Role r : roles) {
				if (r.getName().contains("Club")) {
					server.removeRoleFromMember(server.getMemberById(discord), r).complete();
				}
			}
		} if (Integer.valueOf(newScore) > 350 && role_level < 7) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("350+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 300 && role_level < 6) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("300+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 250 && role_level < 5) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("250+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 200 && role_level < 4) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("200+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 150 && role_level < 3) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("150+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 100 && role_level < 2) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("100+ Club", false).get(0)).complete();
		} else if (Integer.valueOf(newScore) > 50 && role_level < 1) {
			server.addRoleToMember(server.getMemberById(discord), server.getRolesByName("50+ Club", false).get(0)).complete();
		}
	}
	
}
