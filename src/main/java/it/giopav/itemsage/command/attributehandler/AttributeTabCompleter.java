package it.giopav.itemsage.command.attributehandler;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static it.giopav.itemsage.Utils.getAttributeValue;
import static it.giopav.itemsage.Utils.getEquipmentSlotValue;

public class AttributeTabCompleter {

    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasAttributeModifiers();
        if (args.length == 2) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                completions.add(equipmentSlot.name());
            }
        } else if (args.length == 3
                && getEquipmentSlotValue(args[1]) != null) {
            if (condition) {
                for (Attribute attribute : mainHandItem.getItemMeta().getAttributeModifiers(EquipmentSlot.valueOf(args[1])).keySet()) {
                    completions.add(attribute.toString());
                }
            }
            completions.add("add");
        } else if (args.length == 4
                && getEquipmentSlotValue(args[1]) != null
                && !args[2].equalsIgnoreCase("add")
                && condition
                && getAttributeValue(args[2]) != null) {
            completions.add("remove");
            for (AttributeModifier attributeModifier : mainHandItem.getItemMeta().getAttributeModifiers(EquipmentSlot.valueOf(args[1])).get(Attribute.valueOf(args[2]))) {
                completions.add(String.valueOf(attributeModifier.getAmount()));
            }
        }
    }

}