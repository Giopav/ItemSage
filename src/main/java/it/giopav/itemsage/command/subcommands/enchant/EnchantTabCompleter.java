package it.giopav.itemsage.command.subcommands.enchant;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantTabCompleter extends AbstractEnchant implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("add");
            completions.addAll(itemEnchantments(mainHandItem));
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("add")) {
            completions.addAll(allEnchantments(mainHandItem));
        } else if (args.length == 3
                && mainHandItem.getItemMeta().hasEnchants()
                && getEnchantmentValue(args[1]) != null
                && mainHandItem.getItemMeta().hasEnchant(getEnchantmentValue(args[1]))) {
            completions.add("remove");
            completions.add(String.valueOf(mainHandItem.getItemMeta().getEnchantLevel(getEnchantmentValue(args[1]))));
        }
    }

    private List<String> itemEnchantments(ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasEnchants()) {
            return Collections.emptyList();
        }
        List<String> enchantments = new ArrayList<>();
        for (Enchantment enchantment : mainHandItem.getEnchantments().keySet()) {
            enchantments.add(enchantment.getKey().toString().toUpperCase().replace("MINECRAFT:", ""));
        }
        return enchantments;
    }

    private List<String> allEnchantments(ItemStack mainHandItem) {
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