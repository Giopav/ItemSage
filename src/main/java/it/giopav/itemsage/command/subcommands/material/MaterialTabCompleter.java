package it.giopav.itemsage.command.subcommands.material;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MaterialTabCompleter extends AbstractMaterial implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.add("set");
            completions.add(mainHandItem.getType().getKey().toString().toUpperCase().replaceFirst("MINECRAFT:", ""));
        } else if (args.length == 3
                && args[1].equals("set")) {
            completions.addAll(allMaterials());
        }
    }

    private List<String> allMaterials() {
        List<String> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            materials.add(material.toString());
        }
        return materials;
    }
}