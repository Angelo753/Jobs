package cz.angelo.angeljobs;

import cz.angelo.angeljobs.commands.Prace;
import cz.angelo.angeljobs.configurations.Config;
import cz.angelo.angeljobs.configurations.Data;
import cz.angelo.angeljobs.listeners.BlockBreak;
import cz.angelo.angeljobs.listeners.MobKill;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Main extends JavaPlugin {

	public static Main instance;
	public static Set<UUID> cooldown = new HashSet<>();
	public static HashMap<UUID, GamePlayer> gplayer = new HashMap<>();

	private Economy econ;

	@Override
	public void onEnable() {
		instance = this;
		Config.registerConfig();
		Data.registerConfig();
		//Import.registerConfig();
		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Utils.createPlayer(player);
				Utils.createPlayer(player);
				Utils.registerPlayer(player);
			}
		}
		Data.registerConfig();
		this.registerCommands();
		this.registerEvents();
		if (!setupEconomy()) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
	}

	@Override
	public void onDisable() {
		if (Bukkit.getOnlinePlayers().size() > 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Utils.unregisterPlayer(player);
			}
			Data.save();
		}
		gplayer.clear();
		cooldown.clear();
	}

	public void registerEvents(){
		this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		this.getServer().getPluginManager().registerEvents(new GameMechanics(),	this);
		this.getServer().getPluginManager().registerEvents(new MobKill(),	this);
	}

	public void registerCommands(){
		this.getCommand("prace").setExecutor(new Prace());
	}

	public static String Color(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public Economy getEconomy() {
		return econ;
	}

	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
