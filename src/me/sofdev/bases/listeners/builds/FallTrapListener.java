package me.sofdev.bases.listeners.builds;

import me.sofdev.bases.BasesPlugin;
import me.sofdev.bases.command.build.FallTrapCommand;
import me.sofdev.bases.manager.build.*;
import me.sofdev.bases.manager.util.*;
import me.sofdev.bases.menu.FallMenu;
import me.sofdev.bases.manager.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

public class FallTrapListener implements Listener {

    public ArrayList<UUID> ChatConfirm = new ArrayList<>();
    public ArrayList<UUID> BothPositions = new ArrayList<>();
    public static FallTrap Fall = new FallTrap();
    public static MessageManager messages = new MessageManager();
    public UserManager userManager = new UserManager();

    @EventHandler
    public void onInteract(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        if (FallTrapCommand.FallAxe1.contains(player.getUniqueId())) {
            Fall.setPos1(location);
            player.sendMessage(messages.Position1(location));
            player.sendMessage(messages.BreakPos2());
            FallTrapCommand.FallAxe1.remove(player.getUniqueId());
            FallTrapCommand.FallAxe2.add(player.getUniqueId());
            e.setCancelled(true);
        } else if (FallTrapCommand.FallAxe2.contains(player.getUniqueId())) {
            e.setCancelled(true);
            Fall.setPos2(location);
            player.sendMessage(messages.Position2(location));
            player.sendMessage(messages.FallMessageConfirm());
            FallTrapCommand.FallAxe2.remove(player.getUniqueId());
            BothPositions.add(player.getUniqueId());
            ChatConfirm.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater(BasesPlugin.get(), new Runnable() {
                @Override
                public void run() {
                    if (!Fall.FallConfirmed.contains(player.getUniqueId())) {
                        BothPositions.remove(player.getUniqueId());
                        ChatConfirm.remove(player.getUniqueId());
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5f, 1f);
                        player.sendMessage(messages.FallConfirmExpired());
                        userManager.SetFallTrapPlaced(player, false);
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
            if (message.equalsIgnoreCase(messages.FallMessageToConfirm())) {
                Fall.getPos1().setY(1);
                FallMenu.FallColor(player);
                BothPositions.remove(player.getUniqueId());
                ChatConfirm.remove(player.getUniqueId());
                Fall.FallConfirmed.add(player.getUniqueId());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        FallTrapCommand.FallAxe1.remove(event.getEntity().getPlayer().getUniqueId());
        FallTrapCommand.FallAxe2.remove(event.getEntity().getPlayer().getUniqueId());
        BothPositions.remove(event.getEntity().getPlayer().getUniqueId());
        ChatConfirm.remove(event.getEntity().getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FallTrapCommand.FallAxe1.remove(event.getPlayer().getUniqueId());
        FallTrapCommand.FallAxe2.remove(event.getPlayer().getUniqueId());
        BothPositions.remove(event.getPlayer().getUniqueId());
        ChatConfirm.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        FallTrapCommand.FallAxe1.remove(event.getPlayer().getUniqueId());
        FallTrapCommand.FallAxe2.remove(event.getPlayer().getUniqueId());
        BothPositions.remove(event.getPlayer().getUniqueId());
        ChatConfirm.remove(event.getPlayer().getUniqueId());
    }
}
