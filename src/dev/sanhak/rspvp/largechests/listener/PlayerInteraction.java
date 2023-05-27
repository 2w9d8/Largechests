package dev.sanhak.rspvp.largechests.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import dev.sanhak.rspvp.largechests.file.ChestsFile;
import dev.sanhak.rspvp.largechests.utils.ChestsUtils;

public class PlayerInteraction implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (block != null && block.getType() == Material.ENDER_CHEST) {
				event.setCancelled(true);
				Inventory inventory = Bukkit.createInventory(null, 54, "§7enderchest - " + player.getName());

				if (!ChestsFile.get().contains(player.getName())) {
					ChestsFile.get().createSection(player.getName() + ".LargeChests.contents");
					ChestsFile.save();
					player.updateInventory();
					player.openInventory(inventory);
					player.updateInventory();
					player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.5f, 10.0f);
				} else {
					ChestsUtils.loadItems(inventory, player);
					player.updateInventory();
					player.openInventory(inventory);
					player.updateInventory();
					player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.5f, 10.0f);
				}

			}
		}
	}

}
