package cz.angelo.angeljobs.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import cz.angelo.angeljobs.Jobs;
import cz.angelo.angeljobs.Main;
import cz.angelo.angeljobs.Utils;
import cz.angelo.angeljobs.configurations.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class BlockBreak implements Listener {

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled()){
			return;
		}
		if (Main.cooldown.contains(player.getUniqueId())){
			if (Utils.percentChange(0.5)) {
				Utils.sendActionBar(player, Config.getConfig().getString("messages.cooldown"));
			}
			return;
		}
		if (Main.gplayer.get(player.getUniqueId()).getJobs() == Jobs.MINER) {
			if (Config.getConfig().getStringList("jobs.miner.blocks").contains(event.getBlock().getType().toString())) {
				Random rd = new Random();
				if (Utils.percentChange(0.25)) {
					int random = rd.ints(1, 2).findFirst().getAsInt();
					AlonsoLevelsAPI.addExperience(player.getUniqueId(), random);
				}
				if (Utils.percentChange(0.15)) {
					double random = rd.doubles(0.5, 1.00).findFirst().getAsDouble();
					Main.instance.getEconomy().depositPlayer(player, random);
				}
			}

		}
		if (Main.gplayer.get(player.getUniqueId()).getJobs() == Jobs.DIGGER) {
			if (Config.getConfig().getStringList("jobs.digger.blocks").contains(event.getBlock().getType().toString())) {
				Random rd = new Random();
				if (Utils.percentChange(0.15)) {
					int random = rd.ints(1, 2).findFirst().getAsInt();
					AlonsoLevelsAPI.addExperience(player.getUniqueId(), random);
				}
				if (Utils.percentChange(0.15)) {
					double random = rd.doubles(0.5, 1.00).findFirst().getAsDouble();
					Main.instance.getEconomy().depositPlayer(player, random);
				}
			}

		}
		if (Main.gplayer.get(player.getUniqueId()).getJobs() == Jobs.LUMBERJACK) {
			if (Config.getConfig().getStringList("jobs.lumberjack.blocks").contains(event.getBlock().getType().toString())) {
				Random rd = new Random();
				if (Utils.percentChange(0.25)) {
					int random = rd.ints(1, 2).findFirst().getAsInt();
					AlonsoLevelsAPI.addExperience(player.getUniqueId(), random);
				}
				if (Utils.percentChange(0.25)) {
					double random = rd.doubles(0.5, 1.00).findFirst().getAsDouble();
					Main.instance.getEconomy().depositPlayer(player, random);
				}
			}
		}
		if (Main.gplayer.get(player.getUniqueId()).getJobs() == Jobs.FARMER) {
			if (Utils.isFullyGrown(event.getBlock())) {
				Random rd = new Random();
				if (Utils.percentChange(0.15)) {
					int random = rd.ints(1, 2).findFirst().getAsInt();
					AlonsoLevelsAPI.addExperience(player.getUniqueId(), random);
				}
				if (Utils.percentChange(0.15)) {
					double random = rd.doubles(0.5, 1.00).findFirst().getAsDouble();
					Main.instance.getEconomy().depositPlayer(player, random);
				}
			}

		}
	}
}
