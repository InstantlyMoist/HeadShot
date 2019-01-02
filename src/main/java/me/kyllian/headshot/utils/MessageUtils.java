package me.kyllian.headshot.utils;

import org.bukkit.ChatColor;

public class MessageUtils {

    public static String colorTranslate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
