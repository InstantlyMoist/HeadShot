package me.kyllian.headshot.listeners;

import me.kyllian.headshot.HeadShotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByEntityListener implements Listener {

    private HeadShotPlugin plugin;
    private double damageModifer;
    private double snowballDamage;

    public EntityDamageByEntityListener(HeadShotPlugin plugin) {
        this.plugin = plugin;
        damageModifer = plugin.getConfig().getDouble("Headshot.DamageModifier");
        snowballDamage = plugin.getConfig().getDouble("Headshot.SnowballDamage");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        Projectile arrow = (Projectile) event.getDamager();
        if (!(arrow.getShooter() instanceof Player)) return;
        Player shooter = (Player) arrow.getShooter();
        if (!plugin.allowedArrows.contains(arrow.getUniqueId()) && !(arrow instanceof Snowball)) return;
       if (plugin.allowedArrows.contains(arrow.getUniqueId())) plugin.allowedArrows.remove(arrow.getUniqueId());

        // Headshot detection logic
        double arrowY = arrow.getLocation().getY();
        double damagedY = event.getEntity().getLocation().getY();
        // TODO: Maybe add a sound or particle effect (?)
        if (arrowY - damagedY > 1.4D) {
            if (shooter == event.getEntity()) {
                shooter.sendMessage(plugin.getMessageHandler().getCantHeadshotSelfMessage());
                event.setCancelled(true);
                return;
            }
            HeadShotEvent headShotEvent = new HeadShotEvent(event.getEntity(), shooter);
            if (headShotEvent.isCancelled()) return;
            if (arrow instanceof Arrow) event.setDamage(event.getDamage() * damageModifer);
            if (arrow instanceof Snowball && snowballDamage != -1) event.setDamage(snowballDamage);
            shooter.sendMessage(plugin.getMessageHandler().getHeadshotMessage(event.getEntity()));
            if (event.getEntity() instanceof Player) ((Player) event.getEntity()).sendMessage(plugin.getMessageHandler().getBeenHeadshotMessage(shooter));
        }

    }
}
