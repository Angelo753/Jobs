package cz.angelo.angeljobs.listeners;

import com.alonsoaliaga.alonsolevels.api.AlonsoLevelsAPI;
import cz.angelo.angeljobs.Main;
import cz.angelo.angeljobs.Utils;
import cz.angelo.angeljobs.configurations.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;

public class MobKill implements Listener {

	@EventHandler
	public void mobKill(EntityDeathEvent event){
		Entity killer = event.getEntity().getKiller();
		if (killer instanceof Player) {
			Player player = event.getEntity().getKiller();
			List<String> mobs = Config.getConfig().getStringList("jobs.hunter.mobs");
			if (mobs.contains(event.getEntity().getType().toString().toUpperCase())) {
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
	}

}
