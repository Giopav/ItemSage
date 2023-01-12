package it.giopav.itemsage.command.subcommands.help;

import it.giopav.itemsage.Config;
import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HelpTabCompleter extends AbstractHelp implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.addAll(Config.getInstance().getEnabledList());
        }
    }
}