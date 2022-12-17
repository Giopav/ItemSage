package it.giopav.itemsage.command.durabilityhandler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.regex.Pattern;

public class DurabilityExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }
        if (mainHandItem.getType().getMaxDurability() == 0) {
            player.sendMessage(ChatColor.RED + "The item you're holding doesn't have durability.");
            return false;
        }

        if (args.length == 1) {
            return sendDurability(player, mainHandItem);
        } else if (args.length == 3) {
            return setDurability(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendDurability(Player player, ItemStack mainHandItem) {
        int durability = ((Damageable) mainHandItem.getItemMeta()).getDamage();
        player.sendMessage(ChatColor.GREEN + "The item's durability is:");
        player.sendMessage(durabilityMessage(mainHandItem.getType().getMaxDurability()-durability));
        return true;
    }

    private static boolean setDurability(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[2])) {
            player.sendMessage(ChatColor.RED + "You have to input a number.");
            return false;
        }
        int durability = Integer.parseInt(args[2]);
        if (durability < 1) {
            player.sendMessage(ChatColor.RED + "The durability can't be lower than 1.");
            return false;
        }
        int maxDurability = mainHandItem.getType().getMaxDurability();
        if (durability > maxDurability) {
            player.sendMessage(ChatColor.RED + "The durability for this item can't be higher than " + maxDurability + ".");
            return false;
        }

        Damageable mainHandItemMeta = (Damageable) mainHandItem.getItemMeta();
        mainHandItemMeta.setDamage(maxDurability-durability);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The item's durability has been set to:");
        player.sendMessage(durabilityMessage(durability));
        return true;
    }

    private static Component durabilityMessage(int amount) {
        return Component.text(amount)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(String.valueOf(amount)));
    }

}
