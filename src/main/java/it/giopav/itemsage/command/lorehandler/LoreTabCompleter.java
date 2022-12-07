package it.giopav.itemsage.command.lorehandler;

import it.giopav.itemsage.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoreTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(linesCompletion(mainHandItem.getItemMeta()));
        } else if (args.length == 3
                && mainHandItem.getItemMeta().hasLore()
                && Pattern.matches("\\d+", args[1])
                && Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() > Integer.parseInt(args[1])-1) {
            completions.add("remove");
            completions.add(Utils.serializeRightString(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
        }
    }

    private static List<String> linesCompletion(ItemMeta mainHandItemMeta) {
        List<String> linesList = new ArrayList<>();
        if (!mainHandItemMeta.hasLore()) {
            return Collections.emptyList();
        }
        for (int i = 0; i< Objects.requireNonNull(mainHandItemMeta.lore()).size(); i++) {
            linesList.add(String.valueOf(i+1));
        }
        return linesList;
    }

}
