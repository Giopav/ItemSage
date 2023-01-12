package it.giopav.itemsage.command.subcommands.lore;

import it.giopav.itemsage.command.StringUtils;
import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoreTabCompleter extends AbstractLore implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemLoreLines(mainHandItem.getItemMeta()));
        } else if (args.length == 3
                && mainHandItem.getItemMeta().hasLore()
                && Pattern.matches("\\d+", args[1])
                && Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() > Integer.parseInt(args[1])-1) {
            completions.add("remove");
            completions.add(StringUtils.serializeRightString(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
        }
    }

    private List<String> itemLoreLines(ItemMeta mainHandItemMeta) {
        if (!mainHandItemMeta.hasLore()) {
            return Collections.emptyList();
        }
        List<String> lines = new ArrayList<>();
        for (int i = 0; i< Objects.requireNonNull(mainHandItemMeta.lore()).size(); i++) {
            lines.add(String.valueOf(i+1));
        }
        return lines;
    }
}