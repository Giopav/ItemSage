package it.giopav.itemsage.command.attributehandler;

import com.google.common.collect.Multimap;
import it.giopav.itemsage.command.StringUtils;
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
            return sendAttribute(player, args, mainHandItem);
        } else if (args.length > 3) {
            return redirect(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendAllAttributes(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain attributes.");
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
            player.sendMessage(ChatColor.RED + "This item doesn't contain attributes.");
            return false;
        }
        EquipmentSlot equipmentSlot = AttributeHelper.getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" doesn't exist.");
            return false;
        }
        Multimap<Attribute, AttributeModifier> attributeModifierMultimap = mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot);
        if (attributeModifierMultimap.isEmpty()) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1] + "\" doesn't contain attributes.");
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

    private static boolean sendAttribute(Player player, String[] args, ItemStack mainHandItem) {
        if (args[2].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "You must insert an attribute to add.");
            return false;
        }
        if (!mainHandItem.getItemMeta().hasAttributeModifiers()) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain attributes.");
            return false;
        }
        EquipmentSlot equipmentSlot = AttributeHelper.getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + args[1].toUpperCase() + "\" doesn't exist.");
            return false;
        }
        if (mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).isEmpty()) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + equipmentSlot + "\" doesn't contain attributes.");
            return false;
        }
        Attribute attribute = AttributeHelper.getAttributeValue(args[2]);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The attribute \"" + args[2] + "\" doesn't exist.");
            return false;
        }
        if (!mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).containsKey(attribute)) {
            player.sendMessage(ChatColor.RED + "The equipment slot \"" + equipmentSlot + "\" doesn't contain the attribute " + attribute + ".");
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
            return editAttribute(player, mainHandItem, args);
        } else {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
    }

    private static boolean redirectAdd(Player player, String[] args, ItemStack mainHandItem) {
        assert args.length > 3;
        if (args.length < 7) {
            return addAttribute(player,
                    mainHandItem,
                    args[1],
                    args[3],
                    (args.length == 4 ? "1" : args[4]),
                    (args.length == 6 ? args[5] : "ADD_NUMBER"));
        } else {
            player.sendMessage(ChatColor.RED + "There are too many arguments! The last should be " + args[5] + "");
            return false;
        }
    }

    private static boolean addAttribute(Player player, ItemStack mainHandItem, String equipmentSlotString,
                                        String attributeString, String amountString, String operationString) {
        equipmentSlotString = equipmentSlotString.toUpperCase();
        EquipmentSlot slot = AttributeHelper.getEquipmentSlotValue(equipmentSlotString);
        if (slot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + equipmentSlotString + " is not an equipment slot!");
            return false;
        }
        attributeString = attributeString.toUpperCase();
        Attribute attribute = AttributeHelper.getAttributeValue(attributeString);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The argument " + attributeString + " is not an attribute!");
            return false;
        }
        if (!Pattern.matches("(-\\+)?\\d+((.)\\d+)?", amountString)) {
            player.sendMessage(ChatColor.RED + "The argument " + amountString + " should be a number!");
            return false;
        }
        double amount = Double.parseDouble(amountString);
        AttributeModifier.Operation operation = AttributeHelper.getOperationValue(operationString);
        if (operation == null) {
            player.sendMessage(ChatColor.RED + "The argument " + operationString + " is not a valid operation!");
            return false;
        }

        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        AttributeModifier attributeModifier =
                new AttributeModifier(UUID.randomUUID(), attribute.toString(), amount, operation, slot);
        mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The attribute has been added to this item:");
        player.sendMessage(attributeMessage(attribute, attributeModifier));
        return true;
    }

    private static boolean editAttribute(Player player, ItemStack mainHandItem, String[] args) {
        EquipmentSlot slot = AttributeHelper.getEquipmentSlotValue(args[1]);
        if (slot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + args[1].toUpperCase() + " is not an equipment slot!");
            return false;
        }
        Attribute attribute = AttributeHelper.getAttributeValue(args[2]);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The argument " + args[2].toUpperCase() + " is not an attribute!");
            return false;
        }
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        Collection<AttributeModifier> oldAttributeModifiers = mainHandItemMeta.getAttributeModifiers(attribute);
        if (oldAttributeModifiers == null) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain the " + attribute + " attribute!");
            return false;
        }

        String action = args[3];
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
                " " + StringUtils.userFriendlyString(attribute.getKey().toString().split("\\.")[1].replace("_", " "));
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