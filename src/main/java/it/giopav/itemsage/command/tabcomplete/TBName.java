package it.giopav.itemsage.command.tabcomplete;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TBName {
    public static List<String> name(ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasDisplayName();
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            if (condition) {
                completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
            }
            completions.add("set");
        }
        return completions;
    }
}
