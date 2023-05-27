package dev.sanhak.rspvp.largechests.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import dev.sanhak.rspvp.largechests.file.ChestsFile;
import dev.sanhak.rspvp.largechests.utils.ChestsUtils;

public class InventoryClose implements Listener {

	@EventHandler
	public void onCloseEnderChestInventory(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getInventory().getTitle().equalsIgnoreCase("§7enderchest - " + player.getName())) {
			if (!ChestsFile.get().contains(player.getName())) {
				ChestsFile.get().createSection(player.getName() + ".LargeChests.contents");
				ChestsUtils.savePlayerItems(event.getInventory(), player);
				ChestsFile.save();
				player.updateInventory();
				player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.5f, 10.0f);
			} else {
				player.updateInventory();
				ChestsUtils.savePlayerItems(event.getInventory(), player);
				ChestsFile.save();
				player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.5f, 10.0f);
			}
		}
	}
}
