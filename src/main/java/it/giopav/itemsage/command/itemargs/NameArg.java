package it.giopav.itemsage.command.itemargs;

import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

import static it.giopav.itemsage.Utils.deserializeRightString;

public class NameArg {
    public static void nameTabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            if (!mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasDisplayName()) {
                completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
                completions.add(MiniMessage.miniMessage().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
                completions.add(PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(mainHandItem.getItemMeta().displayName())));
            }
            completions.add("set");
        }
    }

    public static boolean nameCommandExecutor(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return args1CommandExecutor(player, mainHandItem);
        } else if (args.length > 2) {
            return argsMoreThan2CommandExecutor(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args1CommandExecutor(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasDisplayName()) {
            player.sendMessage(ChatColor.RED + "This item does not have a name.");
            return false;
        }
        player.sendMessage(ChatColor.GREEN + "The item's name is "
                + ChatColor.RESET + Utils.serializeRightString(mainHandItem.getItemMeta().displayName())
                + ChatColor.GREEN + ".");
        return true;
    }

    private static boolean argsMoreThan2CommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            stringBuilder.append(args[i]).append(" ");
        }
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        mainHandItemMeta.displayName(deserializeRightString(stringBuilder.toString().trim()).decoration(TextDecoration.ITALIC, false));
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The display name has been set to " + ChatColor.RESET + stringBuilder + ".");
        return true;
    }
}