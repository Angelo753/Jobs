package cz.angelo.angeljobs.configurations;

import cz.angelo.angeljobs.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Import {

	private static File file;
	private static FileConfiguration config;

	public static void registerConfig(){
		file = new File(Main.instance.getDataFolder(), "data2.yml");
		if (!file.exists()){
			file.getParentFile().mkdirs();
			Main.instance.saveResource("data2.yml", false);
		}
		config = new YamlConfiguration();
		try {
			config.load(file);
		} catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public static FileConfiguration getConfig(){
		return config;
	}

	public static void save(){
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
