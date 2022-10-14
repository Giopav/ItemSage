package it.giopav.itemsage.command.tabcomplete;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TBEnchant {
    public static List<String> enchant(ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasEnchants();
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            if (condition) {
                for (Enchantment enchantment : mainHandItem.getItemMeta().getEnchants().keySet()) {
                    completions.add(enchantment.getKey().toString());
                }
            }
            completions.add("add");
        } else if (args.length == 3 && condition && containsEnchantment(mainHandItem, args[1])) {
            completions.add("remove");
            completions.add(String.valueOf(mainHandItem.getItemMeta().getEnchantLevel(Objects.requireNonNull(getEnchantment(mainHandItem, args[1])))));
        }
        return completions;
    }

    public static boolean containsEnchantment(ItemStack itemStack, String stringEnchantment) {
        for (Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
            if (enchantment.getKey().toString().equalsIgnoreCase(stringEnchantment)) {
                return true;
            }
        }
        return false;
    }

    public static Enchantment getEnchantment(ItemStack itemStack, String stringEnchantment) {
        for (Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
            if (enchantment.getKey().toString().equalsIgnoreCase(stringEnchantment)) {
                return enchantment;
            }
        }
        return null;
    }
}
