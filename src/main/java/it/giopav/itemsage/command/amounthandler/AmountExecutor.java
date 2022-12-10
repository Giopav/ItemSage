package it.giopav.itemsage.command.amounthandler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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
            return sendAmount(player, mainHandItem);
        } else if (args.length == 3) {
            return setAmount(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendAmount(Player player, ItemStack mainHandItem) {
        int amount = mainHandItem.getAmount();
        player.sendMessage(ChatColor.GREEN + "The item's amount is:");
        player.sendMessage(amountMessage(amount));
        return true;
    }

    private static boolean setAmount(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[2])) {
            player.sendMessage(ChatColor.RED + "You have to input a number.");
            return false;
        }
        int amount = Integer.parseInt(args[2]);
        if (amount < 1) {
            player.sendMessage(ChatColor.RED + "The amount can't be lower than 1.");
            return false;
        }
        if (amount > mainHandItem.getType().getMaxStackSize()) {
            player.sendMessage(ChatColor.RED + "The amount can't be bigger than " + mainHandItem.getType().getMaxStackSize() + ".");
            return false;
        }

        mainHandItem.setAmount(amount);
        player.sendMessage(ChatColor.GREEN + "The item's amount has been set to:");
        player.sendMessage(amountMessage(amount));
        return true;
    }

    private static Component amountMessage(int amount) {
        return Component.text(amount)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(String.valueOf(amount)));
    }

}