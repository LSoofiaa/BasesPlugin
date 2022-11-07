package me.sofdev.bases.manager.util;

import me.sofdev.bases.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;

public class UserManager {

    public FileConfiguration config = BasesPlugin.get().getConfig();

    public void SetBasePlaced(Player player, boolean bool) {
        config.set("Bases." + player.getUniqueId(), bool);
        BasesPlugin.get().saveConfig();
    }

    public boolean HasBasePlaced(Player player) {
        return config.getBoolean("Bases." + player.getUniqueId());
    }

    public void ResetBases() {
        config.set("Bases", null);
        BasesPlugin.get().saveConfig();
    }

    public void SetFallTrapPlaced(Player player, boolean bool) {
        config.set("FallTraps." + player.getUniqueId(), bool);
        BasesPlugin.get().saveConfig();
    }

    public boolean HasFallTrapPlaced(Player player) {
        return config.getBoolean("FallTraps." + player.getUniqueId());
    }

    public void ResetFallTraps() {
        config.set("FallTraps", null);
        BasesPlugin.get().saveConfig();
    }
}