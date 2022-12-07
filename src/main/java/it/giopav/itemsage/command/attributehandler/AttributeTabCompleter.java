package it.giopav.itemsage.command.attributehandler;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static it.giopav.itemsage.Utils.getAttributeValue;
import static it.giopav.itemsage.Utils.getEquipmentSlotValue;

public class AttributeTabCompleter {

    // TODO the attribute function is to be modified and fixed and stuff (make it work, also try to understand it)
    // args.length is always more than 1, if it equals 2 the options are:
    //      - every equipment slot
    // if args.length equals 3, and the second argument is an equipment slot, then the options are:
    //      - add
    //      - old attributes (if the item has any)
    // if args.length equals 4, the item has attribute modifiers, the second argument is an equipment slot, the third
    // argument is not "add", the third argument is an attribute, then the options are:
    //      - remove
    //      - modifier of the attribute
    public static void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                completions.add(equipmentSlot.name());
            }
        } else if (args.length == 3
                && getEquipmentSlotValue(args[1]) != null) {
            if (mainHandItem.getItemMeta().hasAttributeModifiers()) {
                for (Attribute attribute : mainHandItem.getItemMeta().getAttributeModifiers(EquipmentSlot.valueOf(args[1])).keySet()) {
                    completions.add(attribute.toString());
                }
            }
            completions.add("add");
        } else if (args.length == 4
                && mainHandItem.getItemMeta().hasAttributeModifiers()
                && getEquipmentSlotValue(args[1]) != null
                && !args[2].equalsIgnoreCase("add")
                && getAttributeValue(args[2]) != null) {
            completions.add("remove");
            for (AttributeModifier attributeModifier : mainHandItem.getItemMeta().getAttributeModifiers(EquipmentSlot.valueOf(args[1])).get(Attribute.valueOf(args[2]))) {
                completions.add(String.valueOf(attributeModifier.getAmount()));
            }
        }
    }

}