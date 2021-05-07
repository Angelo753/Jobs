package cz.angelo.angeljobs;

import cz.angelo.angeljobs.configurations.Config;
import cz.angelo.angeljobs.configurations.Data;
import cz.angelo.angeljobs.guis.MainGUI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import java.util.UUID;

public class Utils {

	public static void registerPlayer(Player player){
		Jobs job = Jobs.valueOf(Data.getConfig().getString("data." + player.getUniqueId() + ".job"));
		double exp = Data.getConfig().getDouble("data." + player.getUniqueId() + "." + job + ".exp");
		double mexp = Data.getConfig().getDouble("data." + player.getUniqueId() + "." + job + ".mexp");
		double mmoney = Data.getConfig().getDouble("data." + player.getUniqueId() + "." + job + ".mmoney");
		int level = Data.getConfig().getInt("data." + player.getUniqueId() + "." + job + ".level");
		GamePlayer gPlayer = new GamePlayer(player, level, exp, job, mexp, mmoney);
		Main.gplayer.put(player.getUniqueId(), gPlayer);
	}

	public static void unregisterPlayer(Player player){
		UUID uuid = player.getUniqueId();
		double exp = Main.gplayer.get(uuid).getExp();
		double mexp = Main.gplayer.get(uuid).getMexp();
		double mmoney = Main.gplayer.get(uuid).getMmoney();
		int level = Main.gplayer.get(uuid).getLevel();
		Jobs job = Main.gplayer.get(uuid).getJobs();
		Data.getConfig().set("data." + player.getUniqueId() + ".job", job.toString());
		Data.getConfig().set("data." + player.getUniqueId() + "." + job + ".exp", exp);
		Data.getConfig().set("data." + player.getUniqueId() + "." + job + ".level", level);
		Data.getConfig().set("data." + player.getUniqueId() + "." + job + ".mexp", mexp);
		Data.getConfig().set("data." + player.getUniqueId() + "." + job + ".mmoney", mmoney);
		Data.save();
		Main.gplayer.remove(uuid);
		Main.cooldown.remove(uuid);
	}

	public static void createPlayer(Player player){
		if (!playerExists(player)){
			Data.getConfig().set("data." + player.getUniqueId() + ".job", "NONE");
			Data.save();
			Data.registerConfig();
		}
	}

	public static boolean playerExists(Player player){
		if (Data.getConfig().getString("data." + player.getUniqueId()) != null){
			return true;
		}
		return false;
	}
	/*
	public static String getJobs(Player player){
		Jobs job = Main.gplayer.get(player.getUniqueId()).getJobs();
		if (job == Jobs.MINER){
			return "MINER";
		}
		if (job == Jobs.LUMBERJACK){
			return "LUMBERJACK";
		}
		if (job == Jobs.HUNTER){
			return "HUNTER";
		}
		if (job == Jobs.FARMER){
			return "FARMER";
		}
		if (job == Jobs.DIGGER){
			return "DIGGER";
		}
		return "NONE";
	}

	 */

	public static void openMainGUI(Player player){
		MainGUI.open(player);
	}

	public static boolean percentChange(double chance){
		return Math.random() <= chance;
	}

	public static boolean isFullyGrown(Block block){
		MaterialData md = block.getState().getData();
		if (block.getType() == Material.MELON || block.getType() == Material.PUMPKIN){
			return true;
		}
		if (md instanceof Crops){
			return (((Crops)md).getState() == CropState.RIPE);
		}
		return false;
	}

	public static String getJobStatus(Player player, String job){
		if (job.equalsIgnoreCase(Main.gplayer.get(player.getUniqueId()).getJobs().toString())){
			return Config.getConfig().getString("messages.jobStatus.joined");
		}
		return Config.getConfig().getString("messages.jobStatus.clickToJoin");
	}

	public static boolean isCooldown(Player player){
		if (Main.cooldown.contains(player.getUniqueId())){
			return true;
		}
		return false;
	}

	public static void sendActionBar(Player player, String msg){
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Main.Color(msg)));
	}

}
