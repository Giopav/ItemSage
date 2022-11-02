package it.giopav.itemsage.command.enchanthandler;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class EnchantTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasEnchants();
        if (args.length == 2) {
            if (condition) {
                for (Enchantment enchantment : mainHandItem.getEnchantments().keySet()) {
                    completions.add(enchantment.getKey().toString().replace("minecraft:", ""));
                }
            }
            completions.add("add");
        } else if (args.length == 3
                && condition
                && mainHandItem.getItemMeta().getEnchants().containsKey(Enchantment.getByKey(NamespacedKey.fromString(args[1])))) {
            completions.add("remove");
            completions.add(String.valueOf(mainHandItem.getItemMeta().getEnchantLevel(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.fromString(args[1]))))));
        } else if (args.length == 3
                && args[1].equalsIgnoreCase("add")) {
            for (Enchantment enchantment : Enchantment.values()) {
                completions.add(enchantment.getKey().toString().replace("minecraft:", ""));
            }
        }
    }

}
