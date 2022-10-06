package it.giopav.itemsage;

import it.giopav.itemsage.command.CommandHandler;
import it.giopav.itemsage.command.TabCompleteHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ItemSage extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("isage")).setTabCompleter(new TabCompleteHandler());
        Objects.requireNonNull(this.getCommand("isage")).setExecutor(new CommandHandler());
    }
}
