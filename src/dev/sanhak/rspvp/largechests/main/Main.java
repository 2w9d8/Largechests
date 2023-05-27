package dev.sanhak.rspvp.largechests.main;

import org.bukkit.plugin.java.JavaPlugin;

import dev.sanhak.rspvp.largechests.file.ChestsFile;
import dev.sanhak.rspvp.largechests.listener.InventoryClose;
import dev.sanhak.rspvp.largechests.listener.PlayerInteraction;

public class Main extends JavaPlugin {

	public ChestsFile chestsFile;

	@Override
	public void onEnable() {
		chestsFile = new ChestsFile(this, true);
		getServer().getPluginManager().registerEvents(new InventoryClose(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteraction(), this);
	}

}
