package it.giopav.itemsage.command.tabcomplete;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TBMaterial {
    public static List<String> material(ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir();
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            if (condition) {
                completions.add(mainHandItem.getType().getKey().toString());
            }
            completions.add("set");
        }
        return completions;
    }
}
