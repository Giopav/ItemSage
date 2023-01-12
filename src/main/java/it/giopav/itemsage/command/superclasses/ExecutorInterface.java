package it.giopav.itemsage.command.superclasses;

import org.bukkit.entity.Player;

public interface ExecutorInterface {
    boolean command(Player player, String[] args);
}