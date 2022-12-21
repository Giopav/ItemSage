package it.giopav.itemsage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;

import java.util.regex.Pattern;

public class Utils {

    public static Component deserializeRightString(String string) {
        Component component;
        if (Pattern.matches(".*[&§][0-9a-fk-or].*", string)) {
            component = LegacyComponentSerializer.legacyAmpersand().deserialize(string);
        } else {
            component = MiniMessage.miniMessage().deserialize(string);
        }
        return component;
    }

    public static String serializeRightString(Component component) {
        String componentString = PlainTextComponentSerializer.plainText().serialize(component);
        String string;
        if (Pattern.matches(".*[&§][0-9a-fk-or].*", componentString)) {
            string = LegacyComponentSerializer.legacyAmpersand().serialize(component);
        } else {
            string = MiniMessage.miniMessage().serialize(component);
        }
        return string;
    }

    public static Attribute getAttributeValue(String string) {
        Attribute attribute = null;
        for (Attribute attributeElement : Attribute.values()) {
            if (attributeElement.toString().equals(string)) {
                attribute = attributeElement;
                break;
            }
        }
        return attribute;
    }

    public static Enchantment getEnchantmentValue(String string) {
        Enchantment enchantment = null;
        for (Enchantment enchantmentElement : Enchantment.values()) {
            if (enchantmentElement.getKey().toString().replace("minecraft:", "").equalsIgnoreCase(string)) {
                enchantment = enchantmentElement;
                break;
            }
        }
        return enchantment;
    }

    public static EquipmentSlot getEquipmentSlotValue(String string) {
        EquipmentSlot equipmentSlot = null;
        for (EquipmentSlot equipmentSlotElement : EquipmentSlot.values()) {
            if (equipmentSlotElement.name().equalsIgnoreCase(string)) {
                equipmentSlot = equipmentSlotElement;
                break;
            }
        }
        return equipmentSlot;
    }

    public static ItemFlag getItemFlagValue(String string) {
        ItemFlag itemFlag = null;
        for (ItemFlag itemFlagElement : ItemFlag.values()) {
            if (itemFlagElement.toString().equals(string)) {
                itemFlag = itemFlagElement;
                break;
            }
        }
        return itemFlag;
    }

    public static AttributeModifier.Operation getOperationValue(String string) {
        AttributeModifier.Operation operation = null;
        for (AttributeModifier.Operation operationElement : AttributeModifier.Operation.values()) {
            if (operationElement.toString().equals(string)) {
                operation = operationElement;
                break;
            }
        }
        return operation;
    }

    public static String userFriendlyString(String string) {
        StringBuilder userFriendly = new StringBuilder();
        for (String substring : string.split(" ")) {
            userFriendly
                    .append(substring.substring(0, 1).toUpperCase())
                    .append(substring.substring(1))
                    .append(" ");
        }
        return userFriendly.toString().trim();
    }

}