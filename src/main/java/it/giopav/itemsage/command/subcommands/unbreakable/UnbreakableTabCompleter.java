package it.giopav.itemsage.command.subcommands.unbreakable;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UnbreakableTabCompleter extends AbstractUnbreakable implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("true");
            completions.add("false");
        }
    }
}