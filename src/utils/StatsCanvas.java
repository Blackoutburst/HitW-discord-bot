package utils;

import java.awt.Color;

import core.Bot;
import net.dv8tion.jda.api.entities.Member;

public class StatsCanvas {

	/**
	 * Create beta stats image
	 * @param image
	 * @param data
	 */
	public static void createStatsImage(Canvas image, String data, String uuid) {
		int f = API.getFinalsToInt(data);
		int q = API.getQualificationToInt(data);
		int score = q > f ? q : f;
		
		image.drawImage("res/background.png", 0, 0, 1000, 400);
		image.drawImage(Canvas.getHeader(score), 0, 0, 1000, 400);
		
		image.drawImage("res/win.png", 520, 105, 30, 30);
		image.drawImage("res/wall.png", 520, 165, 30, 30);
		image.drawImage("res/q.png", 520, 225, 30, 30);
		image.drawImage("res/f.png", 520, 285, 30, 30);
		image.drawImage("res/qf.png", 520, 345, 30, 30);
		
		
		if (API.getName(data).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 130, 110, 320, 80);
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
		
		GeneralUtils.getPlayerHead(uuid);
		image.drawImage("res/head.png", 20, 100, 100, 100);
		
		image.drawStringLeft("Wins: " + API.getWins(data) + GeneralUtils.getLBPos(API.getUUID(data), 'w'), 560, 130, 30, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + GeneralUtils.getLBPos(API.getUUID(data), 'r'), 560, 190, 30, Color.white);
		image.drawStringLeft("Qualification score: " + API.getQualification(data) + GeneralUtils.getLBPos(API.getUUID(data), 'q'), 560, 250, 30, Color.white);
		image.drawStringLeft("Finals score: " + API.getFinals(data) + GeneralUtils.getLBPos(API.getUUID(data), 'f'), 560, 310, 30, Color.white);
		image.drawStringLeft("Q/F score: " + API.getTotal(data) + GeneralUtils.getLBPos(API.getUUID(data), 't'), 560, 370, 30, Color.white);
		image.save("stats.png");
	}
}
