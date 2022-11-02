package it.giopav.itemsage.command.namehandler;

import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static it.giopav.itemsage.Utils.deserializeRightString;

public class NameExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return args1(player, mainHandItem);
        } else if (args.length > 2) {
            return argsMoreThan2(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args1(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasDisplayName()) {
            player.sendMessage(ChatColor.RED + "This item does not have a name.");
            return false;
        }
        player.sendMessage(ChatColor.GREEN + "The item's name is "
                + ChatColor.RESET + Utils.serializeRightString(mainHandItem.getItemMeta().displayName())
                + ChatColor.GREEN + ".");
        return true;
    }

    private static boolean argsMoreThan2(Player player, String[] args, ItemStack mainHandItem) {
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