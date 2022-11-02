package it.giopav.itemsage.command.amounthandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class AmountExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return args1(player, mainHandItem);
        } else if (args.length == 3) {
            return args3(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args1(Player player, ItemStack mainHandItem) {
        player.sendMessage(ChatColor.GREEN + "The item's amount is " + mainHandItem.getAmount() + ".");
        return true;
    }

    private static boolean args3(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[2])) {
            player.sendMessage(ChatColor.RED + "You have to input a number.");
            return false;
        }
        if (Integer.parseInt(args[2]) < 1) {
            player.sendMessage(ChatColor.RED + "The amount can't be lower than 1.");
            return false;
        }
        if (Integer.parseInt(args[2]) > mainHandItem.getType().getMaxStackSize()) {
            player.sendMessage(ChatColor.RED + "The amount can't be bigger than " + mainHandItem.getType().getMaxStackSize() + ".");
            return false;
        }
        mainHandItem.setAmount(Integer.parseInt(args[2]));
        player.sendMessage(ChatColor.GREEN + "The item's amount has been set to " + args[2] + ".");
        return true;
    }

}