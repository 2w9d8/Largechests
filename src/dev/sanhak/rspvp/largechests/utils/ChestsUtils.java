package dev.sanhak.rspvp.largechests.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.sanhak.rspvp.largechests.file.ChestsFile;
import net.md_5.bungee.api.ChatColor;

public class ChestsUtils {

	public static void savePlayerItems(Inventory inventory, Player player) {
		ConfigurationSection inventorySection = ChestsFile.get()
				.getConfigurationSection(player.getName() + ".LargeChests.contents");
		if (inventorySection == null) {
			inventorySection = ChestsFile.get().createSection(player.getName() + ".LargeChests.contents");
		}

		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack item = inventory.getItem(i);
			if (item == null || item.getType() == Material.AIR) {
				continue;
			}

			ConfigurationSection itemSection = inventorySection.createSection(String.valueOf(i));

			itemSection.set("itemid", item.getType().toString());
			itemSection.set("itemamount", item.getAmount());
			itemSection.set("itemdata", item.getDurability());
			if (item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				if (meta.hasDisplayName()) {
					itemSection.set("name", meta.getDisplayName());
				}
				if (meta.hasLore()) {
					itemSection.set("lore", meta.getLore());
				}
				if (meta.hasEnchants()) {
					List<String> enchantments = new ArrayList<>();
					for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
						enchantments.add(entry.getKey().getName() + "#" + entry.getValue());
					}
					itemSection.set("enchantments", enchantments);
				}
			}
		}

		ChestsFile.save();
	}

	public static void loadItems(Inventory inventory, Player player) {

		ConfigurationSection inventorySection = ChestsFile.get()
				.getConfigurationSection(player.getName() + ".LargeChests.contents");
		if (inventorySection == null) {
			return;
		}

		for (String slotString : inventorySection.getKeys(false)) {
			int slot = Integer.parseInt(slotString);
			ConfigurationSection itemSection = inventorySection.getConfigurationSection(slotString);

			Material material = Material.getMaterial(itemSection.getString("itemid"));
			int amount = itemSection.getInt("itemamount");
			short durability = (short) itemSection.getInt("itemdata");
			ItemStack item = new ItemStack(material, amount, durability);
			ItemMeta meta = item.getItemMeta();
			if (itemSection.contains("itemname")) {
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemSection.getString("itemname")));
			}
			if (itemSection.contains("lore")) {
				List<String> lore = new ArrayList<>();
				for (String loreLine : itemSection.getStringList("lore")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
				}
				meta.setLore(lore);
			}
			if (itemSection.contains("enchantments")) {
				for (String enchantmentString : itemSection.getStringList("enchantments")) {
					String[] parts = enchantmentString.split("#");
					Enchantment enchantment = Enchantment.getByName(parts[0]);
					int level = Integer.parseInt(parts[1]);
					meta.addEnchant(enchantment, level, true);
				}
			}
			item.setItemMeta(meta);
			inventory.setItem(slot, item);
		}
	}

}