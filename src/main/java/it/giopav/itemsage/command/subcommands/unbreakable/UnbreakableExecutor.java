package it.giopav.itemsage.command.subcommands.unbreakable;

import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UnbreakableExecutor extends AbstractUnbreakable implements ExecutorInterface {
    @Override
    public boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1 || args.length ==2) {
            return setUnbreakable(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }
    private boolean setUnbreakable(Player player, String[] args, ItemStack mainHandItem) {
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        String option = args.length == 2 ? args[1] : String.valueOf(!mainHandItemMeta.isUnbreakable());
        if (!option.equalsIgnoreCase("true") && !option.equalsIgnoreCase("false")) {
            player.sendMessage(ChatColor.RED + "You have to input either \"true\" or \"false\".");
            return false;
        }
        boolean unbreakable = Boolean.parseBoolean(option);
        if (mainHandItemMeta.isUnbreakable() == unbreakable) {
            player.sendMessage(ChatColor.RED + "This item is already " + (unbreakable ? "" : "not ") + "unbreakable.");
            return false;
        }

        mainHandItemMeta.setUnbreakable(unbreakable);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "This item " + (unbreakable ? "is now" : "is no longer") + " unbreakable.");
        return true;
    }
}