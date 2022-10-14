package it.giopav.itemsage.command.tabcomplete;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TBAttribute {
    public static List<String> attribute(ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasAttributeModifiers();
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            if (condition) {
                for (Attribute attribute : Objects.requireNonNull(mainHandItem.getItemMeta().getAttributeModifiers()).keys()) {
                    completions.add(attribute.getKey().toString());
                }
            }
            completions.add("add");
        } else if (args.length == 3 && condition && containsAttribute(mainHandItem, args[1])) {
            completions.add("remove");
            for (AttributeModifier attributeModifier : getAttributeModifiers(mainHandItem, args[1])) {
                completions.add(String.valueOf(attributeModifier.getAmount()));
            }
        }
        return completions;
    }

    public static boolean containsAttribute(ItemStack itemStack, String stringAttribute) {
        for (Attribute attribute : Objects.requireNonNull(itemStack.getItemMeta().getAttributeModifiers()).keys()) {
            if (attribute.getKey().toString().equalsIgnoreCase(stringAttribute)) {
                return true;
            }
        }
        return false;
    }

    public static Collection<AttributeModifier> getAttributeModifiers(ItemStack itemStack, String stringAttribute) {
        for (Attribute attribute : Objects.requireNonNull(itemStack.getItemMeta().getAttributeModifiers()).keys()) {
            if (attribute.getKey().toString().equalsIgnoreCase(stringAttribute)) {
                return itemStack.getItemMeta().getAttributeModifiers(Objects.requireNonNull(attribute));
            }
        }
        return Collections.emptyList();
    }
}
