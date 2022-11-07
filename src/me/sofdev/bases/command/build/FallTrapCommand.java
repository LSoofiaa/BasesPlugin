package me.sofdev.bases.command.build;

import me.sofdev.bases.*;
import me.sofdev.bases.listeners.builds.*;
import me.sofdev.bases.manager.messages.*;
import me.sofdev.bases.manager.util.*;
import me.sofdev.bases.utils.chat.*;
import me.sofdev.bases.utils.commands.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;

import java.util.*;

public class FallTrapCommand {

    public static ArrayList<UUID> FallAxe1 = new ArrayList<>();
    public static ArrayList<UUID> FallAxe2 = new ArrayList<>();
    public static FileConfiguration config = BasesPlugin.get().getConfig();
    public MessageManager messages = new MessageManager();
    public UserManager userManager = new UserManager();

    @Command(name = "falltrap", inGameOnly = true)
    public void fall(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.FallTrap"))) {
            if (player.getLocation().getBlockX() < config.getDouble("DEFAULT_OPTIONS.WARZONE")
                    && player.getLocation().getBlockZ() < config.getDouble("DEFAULT_OPTIONS.WARZONE")) {
                player.sendMessage(messages.CantOnWarzone());
                return;
            }

            if (player.getLocation().getBlockY() > config.getInt("DEFAULT_OPTIONS.MIN-Y")) {
                player.sendMessage(messages.GetLower());
                return;
            }

            if (userManager.HasFallTrapPlaced(player)) {
                player.sendMessage(messages.FallPlaced());
                return;
            }

            for (String msg : messages.FallModeEnabled()) {
                player.sendMessage(msg);
            }

            if ( FallTrapListener.Fall.getBuilding().size() > config.getInt("DEFAULT_OPTIONS.MAXIMUM_BASES")) {
                player.sendMessage(CC.translate(" &4\u2502 &f" + FallTrapListener.Fall.getBuilding().size() + " &cfalltraps are being built! &7&o(use /falltraps to see active falltraps.)"));
                return;
            }

            FallAxe1.add(player.getUniqueId());
            userManager.SetFallTrapPlaced(player, true);
        }
    }


    @Command(name = "resetfalltrap", inGameOnly = true)
    public void reset(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.FallTrapAdmin"))) {
            if (args.length() == 0) {
                player.sendMessage(CC.translate("&cUsage: /resetfalltrap <player>"));
                return;
            }

            Player target = Bukkit.getPlayer(args.getArgs(0));

            if (args.length() == 1) {
                userManager.SetFallTrapPlaced(target, false);
                player.sendMessage(messages.RevokedCooldowns(target));
            }
        }
    }

    @Command(name = "resetfalltrapall", inGameOnly = true)
    public void resetall(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.FallTrapAdmin"))) {
            userManager.ResetFallTraps();
            player.sendMessage(messages.RevokedAllCooldowns());
        }
    }

    @Command(name = "falltraps", inGameOnly = true)
    public void falltraps(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.FallTrap"))) {
            player.sendMessage(CC.translate("&d&m----------------"));
            for (Player players :  FallTrapListener.Fall.getBuilding()) {
                if (FallTrapListener.Fall.getBuilding().isEmpty()) {
                    player.sendMessage(CC.translate("&cNo falltraps are building now."));
                    return;
                }
                player.sendMessage(CC.translate("&a" + players.getName() + "'s falltrap is building."));
            }
            player.sendMessage(CC.translate("&d&m----------------"));
        } else {
            player.sendMessage(CC.translate(config.getString("Permissions.NoPerm")));
        }
    }
}
