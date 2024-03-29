package it.giopav.itemsage.command.subcommands.flag;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FlagTabCompleter implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemFlags(mainHandItem));
        } else if (args.length == 3
                && AbstractFlag.getItemFlagValue(args[1]) != null
                && mainHandItem.hasItemFlag(AbstractFlag.getItemFlagValue(args[1]))) {
            completions.add("remove");
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("add")) {
            completions.addAll(allItemFlags(mainHandItem));
        }
    }

    private List<String> itemFlags(ItemStack mainHandItem) {
        if (mainHandItem.getItemFlags().isEmpty()) {
            return Collections.emptyList();
        }
        List<String> flags = new ArrayList<>();
        for (ItemFlag itemFlag : Objects.requireNonNull(mainHandItem.getItemFlags())) {
            flags.add(itemFlag.toString());
        }
        return flags;
    }

    private List<String> allItemFlags(ItemStack mainHandItem) {
        List<String> flags = new ArrayList<>();
        for (ItemFlag flag : ItemFlag.values()) {
            if (mainHandItem.getItemFlags().contains(flag)) {
                continue;
            }
            flags.add(flag.toString());
        }
        return flags;
    }
}