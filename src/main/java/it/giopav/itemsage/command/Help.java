package it.giopav.itemsage.command;

import org.bukkit.entity.Player;

public class Help {
    public static boolean help(Player player) {
        player.sendMessage("help");
        return true;
    }
}
