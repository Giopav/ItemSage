package it.giopav.itemsage;

import it.giopav.itemsage.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemSage extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("isage").setExecutor(new CommandHandler());
    }
}
