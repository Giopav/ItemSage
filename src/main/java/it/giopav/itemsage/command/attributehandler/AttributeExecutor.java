package it.giopav.itemsage.command.attributehandler;

import com.google.common.collect.Multimap;
import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

public class AttributeExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendAllAttributes(player, mainHandItem);
        } else if (args.length == 2) {
            return sendSlotAttributes(player, args, mainHandItem);
        } else if (args.length == 3) {
            return sendAttributes(player, args, mainHandItem);
        } else if (args.length > 3) {
            return redirect(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    // For every equipment slot:
    //     For every attribute in that slot:
    //         For every modifier of that attribute:
    //             Send it in chat!
    private static boolean sendAllAttributes(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            player.sendMessage(ChatColor.RED + "The item does not contain attributes.");
            return false;
        }

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).isEmpty()) {
                continue;
            }
            Multimap<Attribute, AttributeModifier> attributeModifierMultimap = mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot);
            player.sendMessage((attributeModifierMultimap.size() == 1 ?
                    ChatColor.GREEN + "The attribute of the slot \"" + equipmentSlot + "\" is:" :
                    ChatColor.GREEN + "The attributes of the slot \"" + equipmentSlot + "\" are:"));
            for (Attribute attribute : attributeModifierMultimap.keySet()) {
                Collection<AttributeModifier> attributeModifiers = mainHandItem.getItemMeta().getAttributeModifiers(attribute);
                assert attributeModifiers != null;
                for (AttributeModifier attributeModifier : attributeModifiers) {
                    player.sendMessage(Component.text(ChatColor.GRAY + "» ")
                            .append(attributeMessage(attribute, attributeModifier)));
                }
            }
        }
        return true;
    }

    private static boolean sendSlotAttributes(Player player, String[] args, ItemStack mainHandItem) {
        args[1] = args[1].toUpperCase();
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            player.sendMessage(ChatColor.RED + "The item does not contain attributes.");
            return false;
        }
        EquipmentSlot equipmentSlot = Utils.getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" does not exist.");
            return false;
        }
        Multimap<Attribute, AttributeModifier> attributeModifierMultimap = mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot);
        if (attributeModifierMultimap.isEmpty()) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" does not contain attributes.");
            return false;
        }

        player.sendMessage((attributeModifierMultimap.size() == 1 ?
                ChatColor.GREEN + "The attribute of the slot \"" + equipmentSlot + "\" is:" :
                ChatColor.GREEN + "The attributes of the slot \"" + equipmentSlot + "\" are:"));
        for (Attribute attribute : attributeModifierMultimap.keySet()) {
            Collection<AttributeModifier> attributeModifiers = mainHandItem.getItemMeta().getAttributeModifiers(attribute);
            assert attributeModifiers != null;
            for (AttributeModifier attributeModifier : attributeModifiers) {
                player.sendMessage(Component.text(ChatColor.GRAY + "» ")
                        .append(attributeMessage(attribute, attributeModifier)));
            }
        }
        return true;
    }

    private static boolean sendAttributes(Player player, String[] args, ItemStack mainHandItem) {
        if (args[2].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "You must insert an attribute to add.");
            return false;
        }
        args[1] = args[1].toUpperCase();
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            player.sendMessage(ChatColor.RED + "The item does not contain attributes.");
            return false;
        }
        EquipmentSlot equipmentSlot = Utils.getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" does not exist.");
            return false;
        }
        if (mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).isEmpty()) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + equipmentSlot + "\" does not contain attributes.");
            return false;
        }
        Attribute attribute = Utils.getAttributeValue(args[2]);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The attribute \"" + args[2] + "\" does not exist.");
            return false;
        }
        if (!mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).containsKey(attribute)) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" does not contain the attribute " + attribute + ".");
            return false;
        }

        Collection<AttributeModifier> attributeModifiers = mainHandItem.getItemMeta().getAttributeModifiers(attribute);
        assert attributeModifiers != null;
        player.sendMessage((attributeModifiers.size() == 1 ?
                ChatColor.GREEN + "The modifier of the attribute \"" + attribute + "\" is" :
                ChatColor.GREEN + "The modifiers of the attribute \"" + attribute + "\" are"));
        for (AttributeModifier attributeModifier : attributeModifiers) {
            player.sendMessage(Component.text(ChatColor.GRAY + "» ")
                    .append(attributeMessage(attribute, attributeModifier)));
        }
        return true;
    }

    private static boolean redirect(Player player, String[] args, ItemStack mainHandItem) {
        if (args[2].equalsIgnoreCase("add")) {
            return redirectAdd(player, args, mainHandItem);
        } else if (args.length == 4) {
            return editAttribute(player, mainHandItem, args[1], args[2], args[3]);
        } else {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
    }

    private static boolean redirectAdd(Player player, String[] args, ItemStack mainHandItem) {
        assert args.length > 3;
        if (args.length == 4) {
            return addAttribute(player, mainHandItem, args[1], args[3], "1", "ADD_NUMBER");
        } else if (args.length == 5) {
            return addAttribute(player, mainHandItem, args[1], args[3], args[4], "ADD_NUMBER");
        } else if (args.length == 6) {
            return addAttribute(player, mainHandItem, args[1], args[3], args[4], args[5]);
        } else {
            player.sendMessage(ChatColor.RED + "There are too many arguments! The last should be " + args[5] + "");
            return false;
        }
    }

    private static boolean addAttribute(Player player, ItemStack mainHandItem, String equipmentSlotString,
                                        String attributeString, String amountString, String operationString) {
        equipmentSlotString = equipmentSlotString.toUpperCase();
        EquipmentSlot slot = Utils.getEquipmentSlotValue(equipmentSlotString);
        if (slot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + equipmentSlotString + " is not an equipment slot!");
            return false;
        }
        attributeString = attributeString.toUpperCase();
        Attribute attribute = Utils.getAttributeValue(attributeString);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The argument " + attributeString + " is not an attribute!");
            return false;
        }
        if (!Pattern.matches("(-\\+)?\\d+((.)\\d+)?", amountString)) {
            player.sendMessage(ChatColor.RED + "The argument " + amountString + " should be a number!");
            return false;
        }
        double amount = Double.parseDouble(amountString);
        AttributeModifier.Operation operation = Utils.getOperationValue(operationString);
        if (operation == null) {
            player.sendMessage(ChatColor.RED + "The argument " + operationString + " is not a valid operation!");
            return false;
        }

        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        AttributeModifier attributeModifier =
                new AttributeModifier(UUID.randomUUID(), attribute.toString(), amount, operation, slot);
        mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The attribute has been added to the item:");
        player.sendMessage(attributeMessage(attribute, attributeModifier));
        return true;
    }

    private static boolean editAttribute(Player player, ItemStack mainHandItem, String equipmentSlotString,
                                         String attributeString, String action) {
        equipmentSlotString = equipmentSlotString.toUpperCase();
        EquipmentSlot slot = Utils.getEquipmentSlotValue(equipmentSlotString);
        if (slot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + equipmentSlotString + " is not an equipment slot!");
            return false;
        }
        attributeString = attributeString.toUpperCase();
        Attribute attribute = Utils.getAttributeValue(attributeString);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The argument " + attributeString + " is not an attribute!");
            return false;
        }
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        Collection<AttributeModifier> oldAttributeModifiers = mainHandItemMeta.getAttributeModifiers(attribute);
        if (oldAttributeModifiers == null) {
            player.sendMessage(ChatColor.RED + "The item doesn't contain the " + attribute + " attribute!");
            return false;
        }

        if (Pattern.matches("(-\\+)?\\d+((.)\\d+)?", action)) {
            AttributeModifier oldAttributeModifier = oldAttributeModifiers.iterator().next();
            AttributeModifier attributeModifier =
                    new AttributeModifier(UUID.randomUUID(), attribute.toString(), Double.parseDouble(action), oldAttributeModifier.getOperation(), slot);
            mainHandItemMeta.removeAttributeModifier(attribute);
            mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
            mainHandItem.setItemMeta(mainHandItemMeta);
            player.sendMessage(ChatColor.GREEN + "The attribute has been set to:");
            player.sendMessage(attributeMessage(attribute, attributeModifier));
            return true;
        } else if (action.equalsIgnoreCase("remove")) {
            mainHandItemMeta.removeAttributeModifier(attribute);
            mainHandItem.setItemMeta(mainHandItemMeta);
            player.sendMessage(ChatColor.GREEN + "All of the " + attribute + " modifiers have been removed.");
            return true;
        } else {
            player.sendMessage(ChatColor.RED + "The argument " + action + " should be a number or \"remove\"!");
            return false;
        }
    }

    private static Component attributeMessage(Attribute attribute, AttributeModifier attributeModifier) {
        double modifierAmount = attributeModifier.getAmount();
        String attributeUserFriendly = (modifierAmount < 0 ? "-" : "") +
                (attributeModifier.getOperation() != AttributeModifier.Operation.ADD_NUMBER ? modifierAmount*100 : modifierAmount) +
                " " + Utils.userFriendlyString(attribute.getKey().toString().split("\\.")[1].replace("_", " "));
        String attributeString = attribute + " " +
                (modifierAmount < 0 ? "-" : "") + modifierAmount
                + " " + attributeModifier.getOperation();
        return Component.text()
                .append(Component.text(ChatColor.BLUE + attributeUserFriendly)
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(attributeUserFriendly)))
                .append(Component.text(" " + ChatColor.GRAY + "("))
                .append(Component.text(attributeString)
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(attributeString)))
                .append(Component.text(ChatColor.GRAY + ")"))
                .build();
    }

}