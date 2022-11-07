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

public class BaseCommand {

    public static ArrayList<UUID> BaseAxe1 = new ArrayList<>();
    public static ArrayList<UUID> BaseAxe2 = new ArrayList<>();
    public FileConfiguration config = BasesPlugin.get().getConfig();
    public UserManager userManager = new UserManager();

    public MessageManager messages = new MessageManager();

    @Command(name = "base", inGameOnly = true)
    public void base(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.Base"))) {
            if (player.getLocation().getBlockX() < config.getDouble("DEFAULT_OPTIONS.WARZONE")
                    && player.getLocation().getBlockZ() < config.getDouble("DEFAULT_OPTIONS.WARZONE")) {
                player.sendMessage(messages.CantOnWarzone());
                return;
            }

            if (player.getLocation().getBlockY() > config.getInt("DEFAULT_OPTIONS.MIN-Y")) {
                player.sendMessage(messages.GetLower());
                return;
            }

            if (userManager.HasBasePlaced(player)) {
                player.sendMessage(messages.BasePlaced());
                return;
            }

            for (String msg : messages.BaseModeEnabled()) {
                player.sendMessage(msg);
            }

            if (BaseListener.base.getBuilding().size() > config.getInt("DEFAULT_OPTIONS.MAXIMUM_BASES")) {
                player.sendMessage(CC.translate(" &4\u2502 &f" + BaseListener.base.getBuilding().size() + " &cbases are being built! &7&o(use /bases to see active bases.)"));
                return;
            }
            BaseAxe1.add(player.getUniqueId());
            userManager.SetBasePlaced(player, true);
        }
    }

    @Command(name = "resetbase", inGameOnly = true)
    public void reset(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.BaseAdmin"))) {
            if (args.length() == 0) {
                player.sendMessage(CC.translate("&cUsage: /resetbase <player>"));
                return;
            }

            Player target = Bukkit.getPlayer(args.getArgs(0));

            if (args.length() == 1) {
                userManager.SetBasePlaced(target, false);
                player.sendMessage(messages.RevokedCooldowns(target));
            }
        } else {
            player.sendMessage(CC.translate(config.getString("Permissions.NoPerm")));
        }
    }

    @Command(name = "resetbaseall", inGameOnly = true)
    public void resetAll(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.BaseAdmin"))) {
            userManager.ResetBases();
            player.sendMessage(messages.RevokedAllCooldowns());
        } else {
            player.sendMessage(CC.translate(config.getString("Permissions.NoPerm")));
        }
    }

    @Command(name = "bases", inGameOnly = true)
    public void bases(CommandArgs args) {
        Player player = (Player) args.getSender();
        if (player.hasPermission(config.getString("Permissions.Base"))) {
            player.sendMessage(CC.translate("&d&m----------------"));
            for (Player players : BaseListener.base.getBuilding()) {
                if (BaseListener.base.getBuilding().isEmpty()) {
                    player.sendMessage(CC.translate("&cNo bases are building now."));
                    return;
                }
                player.sendMessage(CC.translate("&a" + players.getName() + "'s base is building."));
            }
            player.sendMessage(CC.translate("&d&m----------------"));
        } else {
            player.sendMessage(CC.translate(config.getString("Permissions.NoPerm")));
        }
    }
}
