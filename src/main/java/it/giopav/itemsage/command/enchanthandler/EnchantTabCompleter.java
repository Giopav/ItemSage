package it.giopav.itemsage.command.enchanthandler;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

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
            if (mainHandItem.getItemMeta().hasEnchants()) {
                for (Enchantment enchantment : mainHandItem.getEnchantments().keySet()) {
                    completions.add(enchantment.getKey().toString().replace("minecraft:", ""));
                }
            }
        } else if (args.length == 3
                && mainHandItem.getItemMeta().hasEnchants()
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
