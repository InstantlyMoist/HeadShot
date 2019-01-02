package me.kyllian.headshot.listeners;

import me.kyllian.headshot.HeadShotPlugin;
import me.kyllian.headshot.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityShootBowListener implements Listener {

    private HeadShotPlugin plugin;

    public EntityShootBowListener(HeadShotPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!plugin.isEnchantmentEnabled()) plugin.allowedArrows.add(event.getProjectile().getUniqueId());
        if (event.getBow().getItemMeta().hasLore() && event.getBow().getItemMeta().getLore().contains(MessageUtils.colorTranslate(plugin.getHeadShotEnchant().getDisplayName()))) {
            plugin.allowedArrows.add(event.getProjectile().getUniqueId());
        }
    }
}
