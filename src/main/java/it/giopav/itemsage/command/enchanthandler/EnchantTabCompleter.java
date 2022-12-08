package it.giopav.itemsage.command.enchanthandler;

import it.giopav.itemsage.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantTabCompleter {

    // args.length is always more than 1, if it equals 2 the options are:
    //      - add
    //      - old enchantments (if the item has any)
    // if args.length equals 3, and the second argument is "add", then the options are:
    //      - every enchantment
    // if args.length equals 3, the second argument is an enchantment and the enchantment is already on the item,
    // then the options are:
    //      - remove
    //      - old level
    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemEnchantments(mainHandItem));
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("add")) {
            completions.addAll(allEnchantments(mainHandItem));
        } else if (args.length == 3
                && mainHandItem.getItemMeta().hasEnchants()
                && Utils.getEnchantmentValue(args[1]) != null
                && mainHandItem.getItemMeta().hasEnchant(Utils.getEnchantmentValue(args[1]))) {
            completions.add("remove");
            completions.add(String.valueOf(mainHandItem.getItemMeta().getEnchantLevel(Utils.getEnchantmentValue(args[1]))));
        }
    }

    private static List<String> itemEnchantments(ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasEnchants()) {
            return Collections.emptyList();
        }
        List<String> enchantments = new ArrayList<>();
        for (Enchantment enchantment : mainHandItem.getEnchantments().keySet()) {
            enchantments.add(enchantment.getKey().toString().toUpperCase().replace("MINECRAFT:", ""));
        }
        return enchantments;
    }

    private static List<String> allEnchantments(ItemStack mainHandItem) {
        List<String> enchantments = new ArrayList<>();
        for (Enchantment enchantment : Enchantment.values()) {
            if (mainHandItem.containsEnchantment(enchantment)) {
                continue;
            }
            enchantments.add(enchantment.getKey().toString().toUpperCase().replace("MINECRAFT:", ""));
        }
        return enchantments;
    }

}
