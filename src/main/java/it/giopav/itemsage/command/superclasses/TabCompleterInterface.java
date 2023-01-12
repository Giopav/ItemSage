package it.giopav.itemsage.command.superclasses;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface TabCompleterInterface {
    void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args);
}