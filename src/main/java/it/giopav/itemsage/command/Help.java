package it.giopav.itemsage.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {
    public static boolean help(Player player, String[] args) {
        player.sendMessage(ChatColor.GREEN + "How to use /isage:");
        player.sendMessage(ChatColor.GREEN + "/isage help - Show this menù");
        player.sendMessage(ChatColor.GREEN + "/isage help name - Show the name command menù");
        return true;
    }

    public static boolean name(Player player) {
        player.sendMessage(ChatColor.GREEN + "How to use /isage name:");
        player.sendMessage(ChatColor.GREEN + "/isage name - Show this menù");
        player.sendMessage(ChatColor.GREEN + "/isage name get - Get the item name");
        player.sendMessage(ChatColor.GREEN + "/isage name set [name] - Set the item name");
        player.sendMessage(ChatColor.GREEN + "/isage name remove - Remove the item name");
        return true;
    }
}
