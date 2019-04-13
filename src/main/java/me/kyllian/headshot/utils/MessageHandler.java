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

    public void sendHeadshotMessage(Entity entity) {
        String message = MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.Headshot").replace("%player%", entity instanceof Player ? ((Player) entity).getName() : getEntityName(entity)));
        if (!message.equalsIgnoreCase("none")) entity.sendMessage(message);
    }

    public void sendBeenHeadshotMessage(Player player) {
        String message = MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.BeenHeadshot").replace("%player%", player.getName()));
        if (!message.equalsIgnoreCase("none")) player.sendMessage(message);
    }

    public void sendCantHeadshotSelfMessage(Player player) {
        String message = MessageUtils.colorTranslate(plugin.getConfig().getString("Messages.CantHeadshotSelf"));
        if (!message.equalsIgnoreCase("none")) player.sendMessage(message);
    }

    public String getEntityName(Entity entity) {
        return Bukkit.getVersion().contains("1.7") ? entity.getType().getName() : entity.getName();
    }
}
