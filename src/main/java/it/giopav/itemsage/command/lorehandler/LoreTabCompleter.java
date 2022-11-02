package it.giopav.itemsage.command.lorehandler;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoreTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasLore();
        if (args.length == 2) {
            if (condition) {
                for (int i = 0; i< Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size(); i++) {
                    completions.add(String.valueOf(i+1));
                }
            }
            completions.add("add");
        } else if (args.length == 3
                && condition
                && Pattern.matches("\\d+", args[1])
                && Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() > Integer.parseInt(args[1])-1) {
            completions.add("remove");
            completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
            completions.add(MiniMessage.miniMessage().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
            completions.add(PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
        }
    }

}
