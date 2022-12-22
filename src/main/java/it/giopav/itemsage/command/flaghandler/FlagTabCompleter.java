package it.giopav.itemsage.command.flaghandler;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FlagTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemFlags(mainHandItem));
        } else if (args.length == 3
                && FlagHelper.getItemFlagValue(args[1]) != null
                && mainHandItem.hasItemFlag(FlagHelper.getItemFlagValue(args[1]))) {
            completions.add("remove");
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("add")) {
            completions.addAll(allItemFlags(mainHandItem));
        }
    }

    private static List<String> itemFlags(ItemStack mainHandItem) {
        if (mainHandItem.getItemFlags().isEmpty()) {
            return Collections.emptyList();
        }
        List<String> flags = new ArrayList<>();
        for (ItemFlag itemFlag : Objects.requireNonNull(mainHandItem.getItemFlags())) {
            flags.add(itemFlag.toString());
        }
        return flags;
    }

    private static List<String> allItemFlags(ItemStack mainHandItem) {
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