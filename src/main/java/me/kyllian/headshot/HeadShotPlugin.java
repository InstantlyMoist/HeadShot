package me.kyllian.headshot;

import me.kyllian.headshot.enchant.HeadShotEnchant;
import me.kyllian.headshot.listeners.EnchantItemListener;
import me.kyllian.headshot.listeners.EntityDamageByEntityListener;
import me.kyllian.headshot.listeners.EntityShootBowListener;
import me.kyllian.headshot.utils.MessageHandler;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class HeadShotPlugin extends JavaPlugin {

    public HeadShotEnchant headShotEnchant;
    private MessageHandler messageHandler;
    public List<UUID> allowedArrows;
    private boolean enchantmentEnabled;
    public int numberOfHeadShots = 0;

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

        Metrics metrics = new Metrics(this);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SingleLineChart("number_of_headshots_made", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return numberOfHeadShots;
            }
        }));
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
