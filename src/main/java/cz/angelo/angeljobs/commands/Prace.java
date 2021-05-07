package cz.angelo.angeljobs.commands;

import cz.angelo.angeljobs.Main;
import cz.angelo.angeljobs.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Prace implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		Utils.openMainGUI(player);
		player.sendMessage("exp: " + Main.gplayer.get(player.getUniqueId()).getExp() + ", level: " + Main.gplayer.get(player.getUniqueId()).getLevel());
		return false;
	}
}
