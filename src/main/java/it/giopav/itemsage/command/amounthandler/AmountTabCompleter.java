package it.giopav.itemsage.command.amounthandler;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AmountTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.add(String.valueOf(mainHandItem.getAmount()));
        }
    }

}
