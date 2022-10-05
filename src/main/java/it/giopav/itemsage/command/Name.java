package it.giopav.itemsage.command;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Name {
    public static boolean name(Player player, String[] args) {
        if (player.getEquipment().getItemInMainHand().getType() != Material.AIR && args.length > 1) {
            switch (args[1].toLowerCase()) {
                case "get":
                    return get(player);
                case "set":
                    return set(player, args);
                case "remove0":
                    return remove0(player);
                case "remove1":
                    return remove1(player);
                default:
                    return Help.name(player);
            }
        }
        return Help.name(player);
    }

    public static boolean get(Player player) {
        ItemMeta itemMeta = player.getEquipment().getItemInMainHand().getItemMeta();
        if (itemMeta.hasDisplayName()) {
            player.sendMessage(ChatColor.GREEN + "» " + ChatColor.WHITE + itemMeta.displayName());
            return true;
        }
        player.sendMessage(ChatColor.RED + "The item does not have a name.");
        return false;
    }

    public static boolean set(Player player, String[] args) {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();
        if (itemStack.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must have an item in your main hand.");
            return false;
        }
        if (args.length == 2) {
            player.sendMessage(ChatColor.RED + "Use the command remove if you want to strip the item's name.");
            return false;
        }
        StringBuilder nameBuilder = new StringBuilder();
        for(String arg : args) {
            nameBuilder.append(" ").append(arg);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(nameBuilder.toString().replace("&", "§").trim()));
        itemStack.setItemMeta(itemMeta);
        return true;
    }

    public static boolean remove0(Player player) {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();
        if (itemStack.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must have an item in your main hand.");
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName();
        itemStack.setItemMeta(itemMeta);
        return true;
    }

    public static boolean remove1(Player player) {
        ItemStack itemStack = player.getEquipment().getItemInMainHand();
        if (itemStack.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must have an item in your main hand.");
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(null);
        itemStack.setItemMeta(itemMeta);
        return true;
    }
}
