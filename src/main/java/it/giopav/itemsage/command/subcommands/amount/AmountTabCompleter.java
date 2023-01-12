package it.giopav.itemsage.command.subcommands.amount;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AmountTabCompleter extends AbstractAmount implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.add(String.valueOf(mainHandItem.getAmount()));
        }
    }
}