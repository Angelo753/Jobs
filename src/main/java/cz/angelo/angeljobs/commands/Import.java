package cz.angelo.angeljobs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class Import implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ConfigurationSection section = cz.angelo.angeljobs.configurations.Import.getConfig().getConfigurationSection("job");

		return false;
	}
}
