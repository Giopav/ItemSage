package it.giopav.itemsage.command.materialhandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MaterialExecutor {

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
        player.sendMessage(ChatColor.GREEN + "The item's material is " + mainHandItem.getType().getKey() + ".");
        return true;
    }

    private static boolean args3(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        args[2] = args[2].toUpperCase().replaceFirst("MINECRAFT:", "");
        if (Material.getMaterial(args[2]) == null) {
            player.sendMessage(ChatColor.RED + "You have to input a valid material.");
            return false;
        }
        mainHandItem.setType(Objects.requireNonNull(Material.getMaterial(args[2])));
        player.sendMessage(ChatColor.GREEN + "The item's material has been set to " + args[2] + ".");
        return true;
    }
}