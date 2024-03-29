package it.giopav.itemsage.command.subcommands.durability;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DurabilityTabCompleter extends AbstractDurability implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.addAll(itemDurability(mainHandItem));
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("set")) {
            completions.addAll(commonDurability(mainHandItem));
        }
    }

    private List<String> itemDurability(ItemStack mainHandItem) {
        if (mainHandItem.getType().getMaxDurability() != 0) {
            int durability = ((Damageable) mainHandItem.getItemMeta()).getDamage();
            return Collections.singletonList(String.valueOf(mainHandItem.getType().getMaxDurability()-durability));
        } else {
            return Collections.emptyList();
        }
    }

    private List<String> commonDurability(ItemStack mainHandItem) {
        if (mainHandItem.getType().getMaxDurability() != 0) {
            int maxDurability = mainHandItem.getType().getMaxDurability();
            return Arrays.asList(String.valueOf(maxDurability), String.valueOf(maxDurability/2), "1");
        } else {
            return Collections.emptyList();
        }
    }
}
