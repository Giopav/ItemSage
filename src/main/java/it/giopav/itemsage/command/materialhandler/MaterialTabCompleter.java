package it.giopav.itemsage.command.materialhandler;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MaterialTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir();
        if (args.length == 2) {
            if (condition) {
                completions.add(mainHandItem.getType().getKey().toString());
            }
            completions.add("set");
        }
    }

}
