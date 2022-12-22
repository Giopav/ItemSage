package it.giopav.itemsage.command.enchanthandler;

import org.bukkit.enchantments.Enchantment;

public class EnchantHelper {

    public static Enchantment getEnchantmentValue(String string) {
        Enchantment enchantment = null;
        for (Enchantment enchantmentElement : Enchantment.values()) {
            if (enchantmentElement.getKey().toString().replace("minecraft:", "").equalsIgnoreCase(string)) {
                enchantment = enchantmentElement;
                break;
            }
        }
        return enchantment;
    }

}
