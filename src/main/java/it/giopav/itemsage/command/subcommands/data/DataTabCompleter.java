package it.giopav.itemsage.command.subcommands.data;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTabCompleter extends AbstractData implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.addAll(removeData(mainHandItem));
        } else if (args.length == 3
                && !mainHandItem.getItemMeta().getPersistentDataContainer().isEmpty()
                && args[1].equalsIgnoreCase("remove")) {
            completions.addAll(allData(mainHandItem));
        }
    }

    private List<String> removeData(ItemStack mainHandItem) {
        if (mainHandItem.getItemMeta().getPersistentDataContainer().isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList("remove");
        }
    }

    private List<String> allData(ItemStack mainHandItem) {
        List<String> data = new ArrayList<>();
        for (NamespacedKey key : mainHandItem.getItemMeta().getPersistentDataContainer().getKeys()) {
            data.add(key.toString());
        }
        return data;
    }
}
