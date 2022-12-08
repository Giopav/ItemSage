package it.giopav.itemsage.command.flaghandler;

import it.giopav.itemsage.Utils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FlagTabCompleter {

    // args.length is always more than 1, if it equals 2 the options are:
    //      - add
    //      - old flags (if the item has any)
    // if args.length equals 3, the item contains flags, the second argument is a flag and the flag is contained in
    // the item, then the only options are:
    //      - remove
    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemFlags(mainHandItem));
        } else if (args.length == 3
                && !mainHandItem.getItemFlags().isEmpty()
                && Utils.getItemFlagValue(args[1]) != null
                && mainHandItem.hasItemFlag(Utils.getItemFlagValue(args[1]))) {
            completions.add("remove");
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

}