package it.giopav.itemsage.command.subcommands.name;

import it.giopav.itemsage.command.StringUtils;
import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class NameTabCompleter extends AbstractName implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.addAll(itemDisplayName(mainHandItem.getItemMeta()));
        }
    }

    private List<String> itemDisplayName(ItemMeta mainHandItemMeta) {
        if (mainHandItemMeta.hasDisplayName()) {
            return Collections.singletonList(StringUtils.serializeRightString(mainHandItemMeta.displayName()));
        }
        return Collections.emptyList();
    }
}