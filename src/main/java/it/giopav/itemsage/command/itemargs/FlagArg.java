package it.giopav.itemsage.command.itemargs;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class FlagArg {
    public static void flagTabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && !mainHandItem.getItemFlags().isEmpty();
        if (args.length == 2) {
            if (condition) {
                for (ItemFlag itemFlag : Objects.requireNonNull(mainHandItem.getItemFlags())) {
                    completions.add(itemFlag.toString());
                }
            }
            completions.add("add");
        } else if (args.length == 3 && condition && containsItemFlag(mainHandItem, args[1])) {
            completions.add("remove");
        }
    }

    private static boolean containsItemFlag(ItemStack itemStack, String flag) {
        for (ItemFlag itemFlag : itemStack.getItemMeta().getItemFlags()) {
            if (itemFlag.toString().equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }
}
