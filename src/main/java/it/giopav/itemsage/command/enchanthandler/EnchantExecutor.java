package it.giopav.itemsage.command.enchanthandler;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class EnchantExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 3) {
            return args3(player, args, mainHandItem);
        } else if (args.length == 4) {
            return args4(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args3(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return args3Add(player, args, mainHandItem);
        }
        args[1] = args[1].toLowerCase();
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[1]));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[1] + " doesn't exist.");
            return false;
        }
        if (!mainHandItem.getItemMeta().getEnchants().containsKey(Enchantment.getByKey(NamespacedKey.fromString(args[1])))) {
            player.sendMessage(ChatColor.RED + "The item does not have the enchant " + enchantment.getKey() + ".");
            return false;
        }

        if (args[2].equalsIgnoreCase("remove")) {
            mainHandItem.removeEnchantment(enchantment);
            player.sendMessage(ChatColor.GREEN + "The enchantment " + enchantment.getKey() + " has been removed.");
            return true;
        } else if (Pattern.matches("\\d+", args[2])) {
            mainHandItem.addUnsafeEnchantment(enchantment, Integer.parseInt(args[2]));
            player.sendMessage(ChatColor.GREEN + "The enchantment " + enchantment.getKey() + " " + args[2] + " has been added to the item.");
            return true;
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args3Add(Player player, String[] args, ItemStack mainHandItem) {
        args[2] = args[2].toLowerCase();
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[2]));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[2] + " doesn't exist.");
            return false;
        }
        mainHandItem.addUnsafeEnchantment(enchantment, enchantment.getStartLevel());
        player.sendMessage(ChatColor.GREEN + "The enchantment " + enchantment.getKey() + " " + enchantment.getStartLevel() + " has been added to the item.");
        return true;
    }

    private static boolean args4(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        args[2] = args[2].toLowerCase();
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[2]));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[2] + " doesn't exist.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[3])) {
            player.sendMessage(ChatColor.RED + "The level has to be an integer. (not " + args[3] + "!)");
            return false;
        }
        if (Integer.parseInt(args[3]) < enchantment.getStartLevel()) {
            player.sendMessage(ChatColor.RED + "The level can't be lower that " + enchantment.getStartLevel() + ".");
            return false;
        }
        if (Integer.parseInt(args[3]) > 100) {
            player.sendMessage(ChatColor.RED + "The level can't be higher that " + 100 + ".");
            return false;
        }
        mainHandItem.addUnsafeEnchantment(enchantment, Integer.parseInt(args[3]));
        player.sendMessage(ChatColor.GREEN + "The enchantment " + enchantment.getKey() + " " + args[3] + " has been added to the item.");
        return true;
    }

}