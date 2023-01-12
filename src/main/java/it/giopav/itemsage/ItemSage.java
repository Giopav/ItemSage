package it.giopav.itemsage;

import it.giopav.itemsage.command.ItemsageExecutor;
import it.giopav.itemsage.command.ItemsageTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ItemSage extends JavaPlugin {

    private static ItemSage instance;

    @Override
    public void onEnable() {
        instance = this;

        initializeCommand();
        Bukkit.getServer().getLogger().info("Main command initialized.");

        initializeConfig();
        Bukkit.getServer().getLogger().info("Config file initialized.");
    }

    private void initializeCommand() {
        Objects.requireNonNull(this.getCommand("itemsage")).setTabCompleter(new ItemsageTabCompleter());
        Objects.requireNonNull(this.getCommand("itemsage")).setExecutor(new ItemsageExecutor());
    }

    private void initializeConfig() {
        saveDefaultConfig();
    }

    public static ItemSage getInstance() {
        return instance;
    }
}