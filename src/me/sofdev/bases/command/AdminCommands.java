package me.sofdev.bases.command;

import me.sofdev.bases.*;
import me.sofdev.bases.utils.chat.*;
import me.sofdev.bases.utils.commands.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class AdminCommands {

    @Command(name = "basesdev", permission = "sofdev.basesdev", inGameOnly = true)
    public void dev(CommandArgs args) {
        Player player = (Player) args.getSender();
        try {
            BasesPlugin.get().reloadConfig();
            player.sendMessage(CC.translate("&cFallTraps has been reloaded."));
            player.sendMessage(CC.translate("&cBases has been reloaded."));
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 4, 1);
        } catch (Exception e) {
            CC.normalLine();
            player.sendMessage(CC.translate("&cSomething went wrong, please contact developer with the error displayed in console."));
            e.printStackTrace();
            CC.normalLine();
        }
    }
}
