package it.giopav.itemsage.command.namehandler;

import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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
            return sendName(player, mainHandItem);
        } else if (args.length > 2)  {
            return setName(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendName(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasDisplayName()) {
            player.sendMessage(ChatColor.RED + "This item does not have a name.");
            return false;
        }

        String serializedName = Utils.serializeRightString(mainHandItem.getItemMeta().displayName());
        player.sendMessage(ChatColor.GREEN + "The item's name is:");
        player.sendMessage(deserializeRightString(serializedName)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(serializedName)));
        return true;
    }

    private static boolean setName(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            nameBuilder.append(args[i]).append(" ");
        }
        String nameString = nameBuilder.toString().trim();
        Component deserializedName = deserializeRightString(ChatColor.RESET + nameString);
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        mainHandItemMeta.displayName(deserializedName);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The display name has been set to:");
        player.sendMessage(deserializedName
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(nameString)));
        return true;
    }

}