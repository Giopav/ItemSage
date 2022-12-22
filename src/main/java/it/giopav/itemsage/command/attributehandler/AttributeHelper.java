package it.giopav.itemsage.command.attributehandler;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class AttributeHelper {

    public static Attribute getAttributeValue(String string) {
        Attribute attribute = null;
        for (Attribute attributeElement : Attribute.values()) {
            if (attributeElement.toString().equalsIgnoreCase(string)) {
                attribute = attributeElement;
                break;
            }
        }
        return attribute;
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

}
