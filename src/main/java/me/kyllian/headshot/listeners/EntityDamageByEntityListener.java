package me.kyllian.headshot.listeners;

import me.kyllian.headshot.HeadShotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class EntityDamageByEntityListener implements Listener {

    private HeadShotPlugin plugin;
    private double damageModifer;
    private double snowballDamage;

    // Sound and particles
    private Sound sound;
    private boolean hasSound = false;

    private List<String> disabledWorlds;

    public EntityDamageByEntityListener(HeadShotPlugin plugin) {
        this.plugin = plugin;
        damageModifer = plugin.getConfig().getDouble("Headshot.DamageModifier");
        snowballDamage = plugin.getConfig().getDouble("Headshot.SnowballDamage");
        disabledWorlds = new ArrayList<>(plugin.getConfig().getStringList("HeadShot.DisabledIn"));
        try {
            String soundS  = plugin.getConfig().getString("Headshot.Sound");
            if (soundS.equalsIgnoreCase("NONE")) return;
            sound = Sound.valueOf(soundS);
            hasSound = true;
        } catch (Exception exc) {
            Bukkit.getLogger().warning("Sound incorrect!");
        }


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

                event.setCancelled(true);
                return;
            }
            if (disabledWorlds.contains(shooter.getWorld().getName())) return;
            HeadShotEvent headShotEvent = new HeadShotEvent(event.getEntity(), shooter);
            if (headShotEvent.isCancelled()) return;
            if (arrow instanceof Arrow) event.setDamage(event.getDamage() * damageModifer);
            if (arrow instanceof Snowball && snowballDamage != -1) event.setDamage(snowballDamage);
            plugin.getMessageHandler().sendHeadshotMessage(event.getEntity());
            if (hasSound) shooter.playSound(shooter.getLocation(), sound, 1f, 1f);
            if (!Bukkit.getVersion().contains("1.7")) spawnParticle(event.getEntity().getLocation(), 1);
            if (event.getEntity() instanceof Player) plugin.getMessageHandler().sendBeenHeadshotMessage(shooter);
            plugin.numberOfHeadShots++;
        }

    }

    public void spawnParticle(Location location, int amount) {
        try {
            String particle = plugin.getConfig().getString("Headshot.Particle");
            if (particle.equalsIgnoreCase("NONE")) return;
            location.getWorld().spawnParticle(Particle.valueOf(particle), location, 1);
        } catch (Exception exc) {
            Bukkit.getLogger().warning("Particle incorrect!");
        }
    }
}
