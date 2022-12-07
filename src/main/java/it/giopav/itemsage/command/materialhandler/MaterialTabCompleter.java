package it.giopav.itemsage.command.materialhandler;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MaterialTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.add(mainHandItem.getType().getKey().toString().toUpperCase().replaceFirst("MINECRAFT:", ""));
        } else if (args.length == 3
                && args[1].equals("set")) {
            completions.addAll(materialsCompletion());
        }
    }

    private static List<String> materialsCompletion() {
        List<String> materialList = new ArrayList<>();
        for (Material material : Material.values()) {
            materialList.add(material.toString());
        }
        return materialList;
    }

}
