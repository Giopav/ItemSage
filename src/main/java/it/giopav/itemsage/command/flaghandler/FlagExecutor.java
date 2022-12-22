package it.giopav.itemsage.command.flaghandler;

import it.giopav.itemsage.command.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class FlagExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendFlags(player, mainHandItem);
        } else if (args.length == 3) {
            return redirectAddOrRemove(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendFlags(Player player, ItemStack mainHandItem) {
        Set<ItemFlag> itemFlags = mainHandItem.getItemFlags();
        if (itemFlags.isEmpty()) {
            player.sendMessage(ChatColor.RED + "This item doesn't have any flag.");
            return false;
        }

        if (itemFlags.size() == 1) {
            player.sendMessage(ChatColor.GREEN + "This item's flag is:");
        } else {
            player.sendMessage(ChatColor.GREEN + "This item's flags are:");
        }
        for (ItemFlag itemFlag : itemFlags) {
            player.sendMessage(Component.text(ChatColor.GRAY + "» ")
                    .append(flagMessage(itemFlag)));
        }
        return true;
    }

    private static boolean redirectAddOrRemove(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return addFlag(player, args, mainHandItem);
        } else {
            return removeFlag(player, args, mainHandItem);
        }
    }

    private static boolean addFlag(Player player, String[] args, ItemStack mainHandItem) {
        ItemFlag flag = FlagHelper.getItemFlagValue(args[2]);
        if (flag == null) {
            player.sendMessage(ChatColor.RED + "The flag " + args[2] + " doesn't exist.");
            return false;
        }

        mainHandItem.addItemFlags(flag);
        player.sendMessage(ChatColor.GREEN + "The flag has been added to your item:");
        player.sendMessage(flagMessage(flag));
        return true;
    }

    private static boolean removeFlag(Player player, String[] args, ItemStack mainHandItem) {
        ItemFlag flag = FlagHelper.getItemFlagValue(args[1]);
        if (flag == null) {
            player.sendMessage(ChatColor.RED + "The flag " + args[1] + " doesn't exist.");
            return false;
        }
        if (!mainHandItem.hasItemFlag(flag)) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain the flag " + flag + ".");
            return false;
        }

        mainHandItem.removeItemFlags(flag);
        player.sendMessage(ChatColor.GREEN + "The flag has been removed from your item:");
        player.sendMessage(flagMessage(flag));
        return true;
    }

    private static Component flagMessage(ItemFlag flag) {
        String userFriendlyFlag = StringUtils.userFriendlyString(flag.toString().replace("_", " "));
        return Component.text()
                .append(Component.text(ChatColor.GRAY + userFriendlyFlag)
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(userFriendlyFlag)))
                .append(Component.text(" " + ChatColor.GRAY + "("))
                .append(Component.text(flag.toString())
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(flag.toString())))
                .append(Component.text(ChatColor.GRAY + ")"))
                .build();
    }

}
