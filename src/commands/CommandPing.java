package commands;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import core.Command;
import core.CommandExecutable;

public class CommandPing extends CommandExecutable {

	public CommandPing(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		long time = System.currentTimeMillis();
		Runtime rt = Runtime.getRuntime();
		long memTotal = rt.totalMemory();
		long memMax = rt.maxMemory();
		long memLeft = rt.freeMemory();
		long memUsed = memTotal - memLeft;
		long mbTotal = memTotal / 1048576;
		long mbMax = memMax / 1048576;
		double mbUsed = (double) memUsed / 1048576;
		RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
		long uptime = mxBean.getUptime() / 60000;
		String formatString = "Ping: **%d** ms\nMemory: **%.2f / %d / %d** MiB\nUptime: **%d** minutes";
		
		command.getEvent().getChannel().sendMessage("Pong!").queue(response -> {
			response.editMessageFormat(formatString, System.currentTimeMillis() - time, mbUsed, mbTotal, mbMax, uptime).queue();
		});
		return (true);
	}
}
