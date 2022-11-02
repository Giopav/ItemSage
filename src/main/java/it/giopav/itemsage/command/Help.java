package it.giopav.itemsage.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help {
    public static boolean command(Player player) {
        player.sendMessage(ChatColor.GREEN + "Help placeholder"); //TODO
        return true;
    }
}
