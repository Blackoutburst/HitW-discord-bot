package utils;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import core.Bot;
import net.dv8tion.jda.api.entities.Member;

public class BetaCanvas {

	/**
	 * Create beta stats image
	 * @param image
	 * @param data
	 */
	public static void createBetaStatsImage(Canvas image, String data, String uuid) {
		int f = API.getFinalsToInt(data);
		int q = API.getQualificationToInt(data);
		int score = q > f ? q : f;
		
		image.loadFont("res/beta/font.ttf");
		image.drawImage("res/beta/background.png", 0, 0, 1000, 400);
		image.drawImage(getHeader(score), 0, 0, 1000, 400);
		
		image.drawImage("res/beta/win.png", 520, 105, 30, 30);
		image.drawImage("res/beta/wall.png", 520, 165, 30, 30);
		image.drawImage("res/beta/q.png", 520, 225, 30, 30);
		image.drawImage("res/beta/f.png", 520, 285, 30, 30);
		image.drawImage("res/beta/qf.png", 520, 345, 30, 30);
		
		
		if (API.getName(data).equals("Blackoutburst")) {
			image.drawImage("res/beta/blackout.png", 130, 110, 320, 80);
		} else {
			image.drawStringLeft(API.getName(data), 130, 140, 46, Color.white);
		}
		
		image.drawStringLeft(Stats.getSubTitle(uuid), 130, 180, 36, Color.white);
		
		int lvl = (int) (1.0 + -8750.0 / 2500.0 + Math.sqrt(-8750.0 / 2500.0 * -8750.0 / 2500.0 + 2.0 / 2500.0 * (double)API.getLevelToInt(data)));
		
		
		String discordId = GeneralUtils.getDiscordfromIGN(API.getName(data));
		String discordNick = "N/A";
		if (discordId != null && GeneralUtils.isInsideTheServer(discordId)) {
			Member member = Bot.server.getMemberById(discordId);
			discordNick = member.getEffectiveName() + "#" + member.getUser().getDiscriminator(); 
			discordNick = discordNick.replaceAll("@", "@ ");
		}
		
		if (!discordNick.equals("N/A")) {
			image.drawStringLeft(discordNick, 20, 250, 36, Color.white);
			image.drawStringLeft("Hypixel level: " + lvl, 20, 310, 36, Color.white);
			image.drawStringLeft("Achievement points: " + API.getAP(data), 20, 370, 36, Color.white);
		} else {
			image.drawStringLeft("Hypixel level: " + lvl, 20, 250, 36, Color.white);
			image.drawStringLeft("Achievement points: " + API.getAP(data), 20, 310, 36, Color.white);
		}
		
		getPlayerHead(uuid);
		image.drawImage("res/beta/head.png", 20, 100, 100, 100);
		
		
		image.drawStringLeft("Wins: " + API.getWins(data) + GeneralUtils.getLBPos(API.getUUID(data), 'w'), 560, 130, 30, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + GeneralUtils.getLBPos(API.getUUID(data), 'r'), 560, 190, 30, Color.white);
		image.drawStringLeft("Qualification score: " + API.getQualification(data) + GeneralUtils.getLBPos(API.getUUID(data), 'q'), 560, 250, 30, Color.white);
		image.drawStringLeft("Finals score: " + API.getFinals(data) + GeneralUtils.getLBPos(API.getUUID(data), 'f'), 560, 310, 30, Color.white);
		image.drawStringLeft("Q/F score: " + API.getTotal(data) + GeneralUtils.getLBPos(API.getUUID(data), 't'), 560, 370, 30, Color.white);
		image.save("betastats.png");
	}
	
	/**
	 * Get player head
	 * @param uuid
	 */
	private static void getPlayerHead(String uuid) {
		try {
			URL url = new URL("https://crafatar.com/avatars/" + uuid + "?overlay");
			FileOutputStream fos = new FileOutputStream("res/beta/head.png");
			
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = in.read(buf))) {
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			br.close();
			
			byte[] response = out.toByteArray();
			fos.write(response);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return header color
	 * @param score
	 * @return
	 */
	public static String getHeader(int score) {
		String file = "res/beta/member.png";
		
		if (score >= 50) file = "res/beta/50.png";
		if (score >= 100) file = "res/beta/100.png";
		if (score >= 150) file = "res/beta/150.png";
		if (score >= 200) file = "res/beta/200.png";
		if (score >= 250) file = "res/beta/250.png";
		if (score >= 300) file = "res/beta/300.png";
		if (score >= 350) file = "res/beta/350.png";
		if (score >= 400) file = "res/beta/400.png";
		if (score >= 450) file = "res/beta/450.png";
		if (score >= 500) file = "res/beta/500.png";
		return file;
	}
}
