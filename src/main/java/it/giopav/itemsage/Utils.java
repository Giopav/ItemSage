package it.giopav.itemsage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;

import java.util.regex.Pattern;

public class Utils {

    // Returns the deserialized component of the input string.
    public static Component deserializeRightString(String string) {
        Component component;
        if (Pattern.matches(".*[&§][0-9a-fk-or].*", string)) {
            component = LegacyComponentSerializer.legacyAmpersand().deserialize(string);
        } else {
            component = MiniMessage.miniMessage().deserialize(string);
        }
        return component;
    }

    // Returns the serialized string of the input component.
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

    // Returns the respective attribute from the provided string.
    // If there is none, null is give instead.
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

    // Returns the respective enchantment from the provided string.
    // If there is none, null is give instead.
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

    // Returns the respective equipment slot from the provided string.
    // If there is none, null is give instead.
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
}