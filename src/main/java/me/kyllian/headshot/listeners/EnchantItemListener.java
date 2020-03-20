package me.kyllian.headshot.listeners;

import me.kyllian.headshot.HeadShotPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantItemListener implements Listener {

	private HeadShotPlugin plugin;
	private boolean enabled;
	private double chance;

	public EnchantItemListener(HeadShotPlugin plugin) {
		this.plugin = plugin;
		chance = plugin.getConfig().getDouble("EnchantmentSettings.Chance");
		enabled = plugin.getConfig().getBoolean("EnchantmentSettings.Enabled");
	}

	//TODO: Add a working chance system from the config
	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {
		if (!enabled || event.getItem().getType() != Material.BOW)   return;
		if (event.getExpLevelCost() >= plugin.getHeadShotEnchant().getStartLevel()) {
			// TODO: Add it here
			double percentage = ThreadLocalRandom.current().nextDouble(1);
			if (percentage <= chance) {
				ItemMeta meta = event.getItem().getItemMeta();
				List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
				lore.add(plugin.getHeadShotEnchant().getDisplayName());
				meta.setLore(lore);
				event.getItem().setItemMeta(meta);
			}
		}
	}
}
