package event;

import java.util.Arrays;
import java.util.List;

import commands.CommandManager;
import core.Command;
import core.DisplayPBOnMention;
import main.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import utils.ConfigManager;
import utils.GeneralUtils;

public class MessageReceived {
	
	/**
	 * Execute the event
	 * @param event
	 */
	public void run(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) return;
		if (event.getMember().getUser().isBot()) return;
		if (event.getMessage().getContentRaw().length() == 0) return;
		if (wallyMention(event)) return;
		if (!event.getMessage().getContentRaw().startsWith(Main.PREFIX)) return;
		
		startTyping(event);
		String message = event.getMessage().getContentRaw();
		Member sender = event.getMember();
		String name = getCommandName(message);
		String[] args = getArgs(message);
		
		new CommandManager(new Command(sender, name, args, event));
	}
	
	/**
	 * Check if Wally is mentioned inside the tracker channel
	 * @param event
	 * @return
	 */
	private boolean wallyMention(MessageReceivedEvent event) {
		if (event.getChannel().getId().equals(ConfigManager.getString("trackerChannel"))) {
			List<Member> members = event.getMessage().getMentionedMembers();
			for (Member m : members) {
				if (m.getId().equals(Main.BOT_ID)) {
					new DisplayPBOnMention(event.getMember(), event);
					return (true);
				}
			}
		}
		return (false);
	}
	
	/**
	 * Get command name
	 * @param message
	 * @return
	 */
	private String getCommandName(String message) {
		String str = GeneralUtils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");

		return (strarr[0].substring(Main.PREFIX.length()).toLowerCase());
	}

	private void startTyping(MessageReceivedEvent event) {
		try {
			event.getMessage().getChannel().sendTyping();
		} catch(InsufficientPermissionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get commands arguments
	 * @param message
	 * @return
	 */
	private String[] getArgs(String message) {
		String str = GeneralUtils.removeDuplicateSpace(message);
		String[] strarr = str.split(" ");
		
		return (Arrays.copyOfRange(strarr, 1, strarr.length));
	}
}
