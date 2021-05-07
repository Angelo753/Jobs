package cz.angelo.angeljobs.guis;

import cz.angelo.angeljobs.Main;
import cz.angelo.angeljobs.Utils;
import cz.angelo.angeljobs.configurations.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainGUI {

	public static void open(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, Main.Color(Config.getConfig().getString("gui.main.title")));
		ConfigurationSection items = Config.getConfig().getConfigurationSection("gui.main.items");
		for (String item : items.getKeys(false)) {
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
			inv.setItem(slot, itemStack);
		}
		player.openInventory(inv);
	}

}
