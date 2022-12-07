package it.giopav.itemsage.command.materialhandler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
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
            return sendMaterial(player, mainHandItem);
        } else if (args.length == 3) {
            return setMaterial(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendMaterial(Player player, ItemStack mainHandItem) {
        String materialString = mainHandItem.getType().getKey().toString().toUpperCase().replaceFirst("MINECRAFT:", "");
        player.sendMessage(ChatColor.GREEN + "The item's material is:");
        player.sendMessage(Component.text(materialString)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(materialString)));
        return true;
    }

    private static boolean setMaterial(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        String materialString = args[2].toUpperCase().replaceFirst("MINECRAFT:", "");
        if (Material.getMaterial(materialString) == null) {
            player.sendMessage(ChatColor.RED + "You have to input a valid material.");
            return false;
        }

        mainHandItem.setType(Objects.requireNonNull(Material.getMaterial(materialString)));
        player.sendMessage(ChatColor.GREEN + "The item's material has been set to:");
        player.sendMessage(Component.text(materialString)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(materialString)));
        return true;
    }
}