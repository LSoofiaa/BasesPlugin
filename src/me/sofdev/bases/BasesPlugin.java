package me.sofdev.bases;

import me.sofdev.bases.command.*;
import me.sofdev.bases.command.build.*;
import me.sofdev.bases.listeners.builds.*;
import me.sofdev.bases.utils.chat.*;
import me.sofdev.bases.utils.commands.*;
import org.bukkit.plugin.java.*;

import java.io.*;

public class BasesPlugin extends JavaPlugin {
    String configPath;

    @Override
    public void onEnable() {
        registerConfig();
        CC.log("&C&M----------------");
        CC.log("&DBasesPlugin &a- Loaded");
        CommandFramework framework = new CommandFramework(this);
        CC.log("&cThe configuration is loading, please wait...");
        framework.registerCommands(new BaseCommand());
        getServer().getPluginManager().registerEvents(new BaseListener(), BasesPlugin.get());
        framework.registerCommands(new FallTrapCommand());
        getServer().getPluginManager().registerEvents(new FallTrapListener(), BasesPlugin.get());
        framework.registerCommands(new AdminCommands());
        CC.log("&Dhttps://www.github.com/LSoofiaa");
        CC.log("&c&M----------------");
    }

    @Override
    public void onDisable() {
        CC.log("&C&M----------------");
        CC.log("&DBasesPlugin &a- Disabled");
        CC.log("&DDeveloped by: https://www.github.com/LSoofiaa");
        CC.log("&c&M----------------");
    }

    public void registerConfig() {
        File config = new File(this.getDataFolder(), "config.yml");
        configPath = config.getPath();
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public static BasesPlugin get() {
        return getPlugin(BasesPlugin.class);
    }
}
