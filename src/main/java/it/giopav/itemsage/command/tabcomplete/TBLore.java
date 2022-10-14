package it.giopav.itemsage.command.tabcomplete;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class TBLore {
    public static List<String> lore(ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasLore();
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            if (condition) {
                for (int i = 0; i< Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size(); i++) {
                    completions.add(String.valueOf(i+1));
                }
            }
            completions.add("add");
        } else if (args.length == 3 && condition && containsLine(mainHandItem, args[1])) {
            completions.add("remove");
            completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
        }
        return completions;
    }

    public static boolean containsLine(ItemStack itemStack, String line) {
        return Pattern.matches("\\d+", line) && Objects.requireNonNull(itemStack.getItemMeta().lore()).size() > Integer.parseInt(line)-1;
    }
}
