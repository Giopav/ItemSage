package it.giopav.itemsage.command.itemargs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class MaterialArg {
    public static void materialTabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir();
        if (args.length == 2) {
            if (condition) {
                completions.add(mainHandItem.getType().getKey().toString());
            }
            completions.add("set");
        }
    }

    public static boolean materialCommandExecutor(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return args1CommandExecutor(player, mainHandItem);
        } else if (args.length == 3) {
            return args3CommandExecutor(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args1CommandExecutor(Player player, ItemStack mainHandItem) {
        player.sendMessage(ChatColor.GREEN + "The item's material is " + mainHandItem.getType().getKey() + ".");
        return true;
    }

    private static boolean args3CommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
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