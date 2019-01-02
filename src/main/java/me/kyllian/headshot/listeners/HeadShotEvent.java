package me.kyllian.headshot.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HeadShotEvent extends Event {

    private boolean cancelled;
    private Entity damaged;
    private Player shooter;

    public HeadShotEvent(Entity damaged, Player shooter) {
        super();
        this.damaged = damaged;
        this.shooter = shooter;
    }

    public HeadShotEvent(boolean isAsync) {
        super(isAsync);
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Player getShooter() {
        return shooter;
    }

    public Entity getDamaged() {
        return damaged;
    }


    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
