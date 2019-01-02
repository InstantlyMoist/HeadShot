package me.kyllian.headshot.utils;

import me.kyllian.headshot.HeadShotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MessageHandler {

    private HeadShotPlugin plugin;

    public MessageHandler(HeadShotPlugin plugin) {
        this.plugin = plugin;
    }

    public String getHeadshotMessage(Entity entity) {
        return MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.Headshot").replace("%player%", entity instanceof Player ? ((Player) entity).getName() : getEntityName(entity)));
    }

    public String getBeenHeadshotMessage(Player player) {
        return MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.BeenHeadshot").replace("%player%", player.getName()));
    }

    public String getCantHeadshotSelfMessage() {
        return MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.CantHeadshotSelf"));
    }

    public String getEntityName(Entity entity) {
        return Bukkit.getVersion().contains("1.7") ? entity.getType().getName() : entity.getName();
    }
}
