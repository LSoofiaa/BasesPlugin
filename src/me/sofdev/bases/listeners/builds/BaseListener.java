package me.sofdev.bases.listeners.builds;

import me.sofdev.bases.*;
import me.sofdev.bases.command.build.*;
import me.sofdev.bases.manager.build.*;
import me.sofdev.bases.manager.util.*;
import me.sofdev.bases.menu.*;
import me.sofdev.bases.manager.messages.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

import java.util.*;

public class BaseListener implements Listener {

    public ArrayList<UUID> ChatConfirm = new ArrayList<>();
    public ArrayList<UUID> BothPositions = new ArrayList<>();
    public static Base base = new Base();
    public static MessageManager messages = new MessageManager();
    public UserManager userManager = new UserManager();

    @EventHandler
    public void onInteract(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        if (BaseCommand.BaseAxe1.contains(player.getUniqueId())) {
            base.setPos1(location);
            player.sendMessage(messages.Position1(location));
            player.sendMessage(messages.BreakPos2());
            BaseCommand.BaseAxe1.remove(player.getUniqueId());
            BaseCommand.BaseAxe2.add(player.getUniqueId());
            e.setCancelled(true);
        } else if (BaseCommand.BaseAxe2.contains(player.getUniqueId())) {
            e.setCancelled(true);
            base.setPos2(location);
            player.sendMessage(messages.Position1(location));
            player.sendMessage(messages.BaseMessageConfirm());
            BaseCommand.BaseAxe2.remove(player.getUniqueId());
            BothPositions.add(player.getUniqueId());
            ChatConfirm.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (!base.BaseConfirmed.contains(player.getUniqueId())) {
                        userManager.SetBasePlaced(player, false);
                        BothPositions.remove(player.getUniqueId());
                        ChatConfirm.remove(player.getUniqueId());
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5f, 1f);
                        player.sendMessage(messages.BaseConfirmExpired());
                    }
                }
            }, 600);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();
        if (ChatConfirm.contains(player.getUniqueId()) && BothPositions.contains(player.getUniqueId())) {
            if (message.equalsIgnoreCase(messages.BaseMessageToConfirm())) {
                base.getPos2().setY(player.getLocation().getY() + 20);
                base.getPos1().setY(player.getLocation().getY() - 1);
                BaseMenu.Color(player);
                BothPositions.remove(player.getUniqueId());
                ChatConfirm.remove(player.getUniqueId());
                base.BaseConfirmed.add(player.getUniqueId());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        BaseCommand.BaseAxe1.remove(event.getEntity().getPlayer().getUniqueId());
        BaseCommand.BaseAxe2.remove(event.getEntity().getPlayer().getUniqueId());
        BothPositions.remove(event.getEntity().getPlayer().getUniqueId());
        ChatConfirm.remove(event.getEntity().getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        BaseCommand.BaseAxe1.remove(event.getPlayer().getUniqueId());
        BaseCommand.BaseAxe2.remove(event.getPlayer().getUniqueId());
        BothPositions.remove(event.getPlayer().getUniqueId());
        ChatConfirm.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        BaseCommand.BaseAxe1.remove(event.getPlayer().getUniqueId());
        BaseCommand.BaseAxe2.remove(event.getPlayer().getUniqueId());
        BothPositions.remove(event.getPlayer().getUniqueId());
        ChatConfirm.remove(event.getPlayer().getUniqueId());
    }
}
