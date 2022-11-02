package it.giopav.itemsage.command.namehandler;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class NameTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            if (!mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasDisplayName()) {
                completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
                completions.add(MiniMessage.miniMessage().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
                completions.add(PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
            }
            completions.add("set");
        }
    }

}
