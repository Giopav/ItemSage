package it.giopav.itemsage.command.subcommands.enchant;

import it.giopav.itemsage.command.superclasses.AbstractCommand;
import org.bukkit.enchantments.Enchantment;

public class AbstractEnchant extends AbstractCommand {
    public AbstractEnchant() {
        super(argsArray[4]);
    }

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