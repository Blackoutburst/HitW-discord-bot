package main;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import core.Bot;
import utils.Config;

public class Main {

	private static final String TOKEN = "ODI0OTQ4Mzk1NDUzMzE3MTcw.YF2zEA.BNstbYRIOO5EZDfkfrLkNNnTX_I";
	private static final String ACTIVITY = "Hole in the Woman";

	public static final String BOT_ID = "824948395453317170";
	public static final String API = "2fa7a8b6-fef6-46cf-8585-23e74f7752c7";
	public static final String PREFIX = "!";
	
	public static void main(String[] args) throws LoginException, IOException {
		new Config("config.json");
		new Bot(TOKEN, ACTIVITY);
	}

}
