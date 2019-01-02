package me.kyllian.headshot.enchant;

import me.kyllian.headshot.HeadShotPlugin;
import me.kyllian.headshot.utils.MessageUtils;

public class HeadShotEnchant {

    private HeadShotPlugin plugin;
    private int minimumLevel;
    private double chance;
    private String displayName;

    public HeadShotEnchant(HeadShotPlugin plugin) {
        this.plugin = plugin;
        minimumLevel = plugin.getConfig().getInt("EnchantmentSettings.MinimumLevel");
        chance = plugin.getConfig().getDouble("EnchantmentSettings.Chance");
        displayName = plugin.getConfig().getString("EnchantmentSettings.DisplayName");
    }

    public int getStartLevel() {
        return minimumLevel;
    }

    public String getDisplayName() {
        return MessageUtils.colorTranslate(displayName);
    }

    public double getChance() {
        return chance;
    }
}
