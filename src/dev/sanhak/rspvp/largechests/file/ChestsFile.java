package dev.sanhak.rspvp.largechests.file;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChestsFile {

	private static File file;
	private static FileConfiguration configurationFile;

	public ChestsFile(JavaPlugin Pluginparam, boolean saveDefaultData) {
		file = new File(Pluginparam.getDataFolder(), "chests.yml");
		file.getParentFile().mkdirs();
		if (!(file.exists())) {
			if (saveDefaultData) {
				Pluginparam.saveResource("chests.yml", saveDefaultData);
			} else {
				try {
					file.createNewFile();
				} catch (IOException ioexception) {
					ioexception.printStackTrace();
				}
			}

		}
		configurationFile = YamlConfiguration.loadConfiguration(file);

	}

	public static void save() {
		try {
			configurationFile.save(file);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	public static void reload() {
		configurationFile = YamlConfiguration.loadConfiguration(file);
	}

	public static FileConfiguration get() {
		return configurationFile;
	}
}