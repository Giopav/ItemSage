package it.giopav.itemsage.command.namehandler;

import it.giopav.itemsage.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class NameTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.addAll(itemDisplayName(mainHandItem.getItemMeta()));
        }
    }

    private static List<String> itemDisplayName(ItemMeta mainHandItemMeta) {
        if (mainHandItemMeta.hasDisplayName()) {
            return Collections.singletonList(Utils.serializeRightString(mainHandItemMeta.displayName()));
        }
        return Collections.emptyList();
    }

}
