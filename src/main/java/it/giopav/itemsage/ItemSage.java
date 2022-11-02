package it.giopav.itemsage;

import it.giopav.itemsage.command.ItemsageExecutor;
import it.giopav.itemsage.command.ItemsageTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ItemSage extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("itemsage")).setTabCompleter(new ItemsageTabCompleter());
        Objects.requireNonNull(this.getCommand("itemsage")).setExecutor(new ItemsageExecutor());
    }
}