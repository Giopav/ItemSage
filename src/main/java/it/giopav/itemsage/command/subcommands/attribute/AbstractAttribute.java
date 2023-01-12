package it.giopav.itemsage.command.subcommands.attribute;

import it.giopav.itemsage.command.superclasses.AbstractCommand;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class AbstractAttribute extends AbstractCommand {
    protected AbstractAttribute() {
        super(argsArray[1]);
    }

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
