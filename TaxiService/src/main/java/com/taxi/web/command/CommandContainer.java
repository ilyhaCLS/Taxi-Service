package com.taxi.web.command;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Container class which contains all available commands for this web site.
 *
 */
public class CommandContainer {
	
	private static final Logger log = LoggerFactory.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		
		commands.put("register", new RegisterCommand());
		commands.put("lang", new LangCommand());
		
		// client commands
		commands.put("account", new AccountCommand());
		commands.put("ride", new RideCommand());
		commands.put("rideDetails", new RideDetailsCommand());
		commands.put("rideConfirm", new RideConfirmCommand());
		
		// admin commands
		commands.put("adminPage", new AdminPageCommand());
		commands.put("showRides", new ShowRidesCommand());
		
		log.info("Command container was successfully initialized");
		log.info("Number of commands --> " + commands.size());
	}

	/**
	 * @param commandName
	 * @return Command based on {@code commandName}
	 */
	public static Command get(String commandName) {
		log.info("command: " +commands.get(commandName));
		return commands.get(commandName);
	}
}