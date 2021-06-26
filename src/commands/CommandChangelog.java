package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandChangelog extends CommandExecutable {

	public CommandChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (!new File("changelog.txt").exists()) {
			MessageSender.message(command, "The changelog files doesn't exist");
			return (true);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader("changelog.txt"));
			String changelog = "";
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				changelog += line + "\n";
			}
			reader.close();
			MessageSender.message(command, changelog);
			command.getEvent().getMessage().delete().complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (true);
	}
}
