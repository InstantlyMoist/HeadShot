package me.kyllian.headshot;

import me.kyllian.headshot.enchant.HeadShotEnchant;
import me.kyllian.headshot.listeners.EnchantItemListener;
import me.kyllian.headshot.listeners.EntityDamageByEntityListener;
import me.kyllian.headshot.listeners.EntityShootBowListener;
import me.kyllian.headshot.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HeadShotPlugin extends JavaPlugin {

    public HeadShotEnchant headShotEnchant;
    private MessageHandler messageHandler;
    public List<UUID> allowedArrows;
    private boolean enchantmentEnabled;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        allowedArrows = new ArrayList<>();
        messageHandler = new MessageHandler(this);

        enchantmentEnabled = getConfig().getBoolean("EnchantmentSettings.Enabled");
        headShotEnchant = new HeadShotEnchant(this);
        Bukkit.getPluginManager().registerEvents(new EnchantItemListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityShootBowListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
    }

    public HeadShotEnchant getHeadShotEnchant() {
        return headShotEnchant;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public boolean isEnchantmentEnabled() {
        return enchantmentEnabled;
    }
}
