package it.giopav.itemsage.command.unbreakablehandler;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UnbreakableTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("true");
            completions.add("false");
        }
    }

}
