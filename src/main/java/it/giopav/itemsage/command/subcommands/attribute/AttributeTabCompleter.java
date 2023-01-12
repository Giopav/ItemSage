package it.giopav.itemsage.command.subcommands.attribute;

import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class AttributeTabCompleter extends AbstractAttribute implements TabCompleterInterface {
    @Override
    public void tabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        if (args.length == 2) {
            completions.addAll(allEquipmentSlots());
        } else if (args.length == 3
                && AbstractAttribute.getEquipmentSlotValue(args[1]) != null) {
            completions.add("add");
            completions.addAll(itemAttributes(mainHandItem, EquipmentSlot.valueOf(args[1])));
        } else if (args.length == 4
                && AbstractAttribute.getEquipmentSlotValue(args[1]) != null
                && args[2].equalsIgnoreCase("add")) {
            completions.addAll(allAttributes(mainHandItem, AbstractAttribute.getEquipmentSlotValue(args[1])));
        } else if (args.length == 4
                && AbstractAttribute.getEquipmentSlotValue(args[1]) != null
                && !args[2].equalsIgnoreCase("add")
                && mainHandItem.getItemMeta().hasAttributeModifiers()
                && AbstractAttribute.getAttributeValue(args[2]) != null) {
            completions.add("remove");
            completions.addAll(attributeModifiers(mainHandItem, EquipmentSlot.valueOf(args[1]), Attribute.valueOf(args[2])));
        } else if (args.length == 6
                && AbstractAttribute.getEquipmentSlotValue(args[1]) != null
                && args[2].equalsIgnoreCase("add")
                && AbstractAttribute.getAttributeValue(args[3]) != null
                && Pattern.matches("-?\\d+", args[4])) {
            completions.addAll(allOperations());
        }
    }

    private List<String> allEquipmentSlots() {
        List<String> equipmentSlots = new ArrayList<>();
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            equipmentSlots.add(equipmentSlot.name());
        }
        return equipmentSlots;
    }

    private List<String> itemAttributes(ItemStack mainHandItem, EquipmentSlot equipmentSlot) {
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            return Collections.emptyList();
        }
        List<String> attributes = new ArrayList<>();
        for (Attribute attribute : mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).keySet()) {
            attributes.add(attribute.toString());
        }
        return attributes;
    }

    private List<String> allAttributes(ItemStack mainHandItem, EquipmentSlot slot) {
        List<String> attributes = new ArrayList<>();
        for (Attribute attribute : Attribute.values()) {
            if (mainHandItem.getItemMeta().getAttributeModifiers(slot).containsKey(attribute)) {
                continue;
            }
            attributes.add(attribute.toString());
        }
        return attributes;
    }

    private List<String> attributeModifiers(ItemStack mainHandItem, EquipmentSlot equipmentSlot, Attribute attribute) {
        List<String> attributeModifiers = new ArrayList<>();
        for (AttributeModifier attributeModifier : mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).get(attribute)) {
            attributeModifiers.add(String.valueOf(attributeModifier.getAmount()));
        }
        return attributeModifiers;
    }

    private List<String> allOperations() {
        List<String> operations = new ArrayList<>();
        for (AttributeModifier.Operation operation : AttributeModifier.Operation.values()) {
            operations.add(operation.toString());
        }
        return operations;
    }
}