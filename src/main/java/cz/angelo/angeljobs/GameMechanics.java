package cz.angelo.angeljobs;

import cz.angelo.angeljobs.configurations.Config;
import cz.angelo.angeljobs.configurations.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class GameMechanics implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		Utils.createPlayer(player);
		Utils.registerPlayer(player);
		Data.save();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		Utils.unregisterPlayer(player);
		Data.registerConfig();
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!Main.cooldown.contains(player.getUniqueId())) {
			Main.cooldown.add(player.getUniqueId());
			new BukkitRunnable() {
				@Override
				public void run() {
					Main.cooldown.remove(player.getUniqueId());
					this.cancel();
				}
			}.runTaskLater(Main.instance, 60);
		}
	}

	@EventHandler
	public void invClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		ConfigurationSection titles = Config.getConfig().getConfigurationSection("gui");
		for (String title : titles.getKeys(false)){
			if (event.getView().getTitle().equals(Main.Color(Config.getConfig().getString("gui." + title + ".title")))){
				event.setCancelled(true);
				ConfigurationSection section = Config.getConfig().getConfigurationSection( "gui.main.items");
				for (String slots : section.getKeys(false)){
					if (event.getSlot() == Config.getConfig().getInt("gui.main.items." + slots + ".slot")){
						ConfigurationSection items = Config.getConfig().getConfigurationSection("gui.main.items");
						for (String item : items.getKeys(false)) {
							Jobs jobs = Jobs.valueOf(slots.toUpperCase());
							Main.gplayer.get(player.getUniqueId()).setJobs(jobs);
							Material material = Material.valueOf(Config.getConfig().getString("gui.main.items." + item + ".material"));
							ItemStack itemStack = new ItemStack(material);
							ItemMeta meta = itemStack.getItemMeta();
							meta.setDisplayName(Main.Color(Config.getConfig().getString("gui.main.items." + item + ".name")));
							List<String> lores = Config.getConfig().getStringList("gui.main.items." + item + ".lore");
							List<String> lore = new ArrayList<>();
							for (String s : lores){
								lore.add(Main.Color(s.replace("{STATUS}", Utils.getJobStatus(player, item))));
							}
							meta.setLore(lore);
							meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
							if (item.equalsIgnoreCase(Main.gplayer.get(player.getUniqueId()).getJobs().toString())){
								meta.addEnchant(Enchantment.DURABILITY, 1, false);
								meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							}
							itemStack.setItemMeta(meta);
							int slot = Config.getConfig().getInt("gui.main.items." + item + ".slot");
							event.getInventory().setItem(slot, itemStack);
						}
					}
				}
			}
		}
	}

}
