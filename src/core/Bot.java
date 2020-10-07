package core;


import javax.security.auth.login.LoginException;

import commands.Compare;
import commands.Config;
import commands.Help;
import commands.Pack;
import commands.Ping;
import commands.SetTracker;
import commands.Stats;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {
	// Bot instance
	private JDABuilder bot;
	
	/**
	 * Log the bot and set the activity
	 * @param token
	 * @param activity
	 * @throws LoginException
	 */
	public void login(String token, String activity) throws LoginException {
		bot = JDABuilder.createDefault(token);
		bot.setActivity(Activity.playing(activity));
		bot.addEventListeners(new Bot());
		bot.enableIntents(GatewayIntent.GUILD_MEMBERS);
		bot.build();
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		if (event.getMember().getUser().isBot()) return;
		event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Members", false).get(0)).complete();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getMember().getUser().isBot()) return;
		if (!event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().startsWith("!help"))
				Help.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!pack"))
				Pack.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!stats"))
				Stats.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!ping"))
				Ping.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!compare"))
				Compare.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!getconfig"))
				Config.get(event);
			if (event.getMessage().getContentDisplay().startsWith("!update"))
				Config.update(event);
			if (event.getMessage().getContentDisplay().startsWith("!settracker"))
				SetTracker.set(event);
			if (event.getMessage().getContentDisplay().startsWith("!showtracker"))
				SetTracker.show(event);
		}
    }
	
}
