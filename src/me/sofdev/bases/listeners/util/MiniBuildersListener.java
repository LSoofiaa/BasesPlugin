package me.sofdev.bases.listeners.util;

import me.sofdev.bases.manager.messages.MessageManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class MiniBuildersListener implements Listener {

    public MessageManager messages = new MessageManager();

    @EventHandler
    public void entityDeath(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();

        if (!(e.getDamager() instanceof Player)) return;

        if (entity.getType() != EntityType.ARMOR_STAND) return;

        if (entity.getCustomName() == null) return;

        if (entity.getCustomName().equals(messages.MiniBuildersName())
                || entity.getCustomName().equals(
                messages.MiniBuildersName()) || entity.getCustomName().equals(messages.ClickToMount())) {

            e.setCancelled(true);
            entity.remove();
            entity.getWorld().strikeLightningEffect(entity.getLocation());
        }
    }

    @EventHandler
    public void entityInteract(PlayerInteractAtEntityEvent e) {
        Entity entity = e.getRightClicked();

        if (entity.getType() != EntityType.ARMOR_STAND) return;

        if (entity.getCustomName() == null) return;

        if (entity.getCustomName().equals(messages.MiniBuildersName())
                || entity.getCustomName().equals(
                messages.MiniBuildersName()) || entity.getCustomName().equals(messages.ClickToMount())) {

            e.setCancelled(true);
            entity.setPassenger(e.getPlayer());
        }
    }
}
