package it.giopav.itemsage.command.durabilityhandler;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Collections;
import java.util.List;

public class DurabilityTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.addAll(itemDurability(mainHandItem));
        }
    }

    private static List<String> itemDurability(ItemStack mainHandItem) {
        if (mainHandItem.getType().getMaxDurability() != 0) {
            return Collections.singletonList(String.valueOf(((Damageable) mainHandItem.getItemMeta()).getDamage()));
        } else {
            return Collections.emptyList();
        }
    }

}
