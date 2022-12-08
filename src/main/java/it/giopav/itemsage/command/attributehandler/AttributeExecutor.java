package it.giopav.itemsage.command.attributehandler;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.regex.Pattern;

import static it.giopav.itemsage.Utils.getAttributeValue;
import static it.giopav.itemsage.Utils.getEquipmentSlotValue;

public class AttributeExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 3) {
            return false;
        } else if (args.length == 4) {
            return args4(player, args, mainHandItem);
        } else if (args.length == 5) {
            return args5(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args4(Player player, String[] args, ItemStack mainHandItem) {
        if (args[2].equalsIgnoreCase("add")) {
            return args4Add(player, args, mainHandItem);
        }
        EquipmentSlot equipmentSlot = getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + args[1] + " is not an equipment slot!");
            return false;
        }
        args[2] = args[2].toUpperCase();
        Attribute attribute = getAttributeValue(args[2]);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The attribute " + args[2] + " doesn't exist.");
            return false;
        }
        if (mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).isEmpty()) {
            player.sendMessage(ChatColor.RED + "This item does not have any attribute.");
            return false;
        }
        if (!mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).keySet().contains(attribute)) {
            player.sendMessage(ChatColor.RED + "The item does not contain the attribute " + attribute + " at the slot " + equipmentSlot + ".");
            return false;
        }
        if (args[3].equalsIgnoreCase("remove")) {
            ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
            mainHandItemMeta.removeAttributeModifier(attribute);
            mainHandItem.setItemMeta(mainHandItemMeta);
            player.sendMessage(ChatColor.GREEN + "The attribute " + attribute + " has been removed.");
            return true;
        } else if (Pattern.matches("\\d+", args[3])) {
            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(),"test", 1, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
            ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
            mainHandItemMeta.removeAttributeModifier(attribute, attributeModifier);
            mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
            mainHandItem.setItemMeta(mainHandItemMeta);
            player.sendMessage(ChatColor.GREEN + "The attribute " + attribute + " has been modified.");
            return true;
        }
        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args4Add(Player player, String[] args, ItemStack mainHandItem) {
        EquipmentSlot equipmentSlot = getEquipmentSlotValue(args[1]);
        if (equipmentSlot == null) {
            player.sendMessage(ChatColor.RED + "The argument " + args[1] + " is not an equipment slot!");
            return false;
        }
        args[3] = args[3].toUpperCase();
        Attribute attribute = getAttributeValue(args[3]);
        if (attribute == null) {
            player.sendMessage(ChatColor.RED + "The attribute " + args[3] + " doesn't exist.");
            return false;
        }
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        if (mainHandItem.getItemMeta().getAttributeModifiers() != null
                && mainHandItem.getItemMeta().getAttributeModifiers(equipmentSlot).keys().contains(attribute)) {
            player.sendMessage(ChatColor.RED + "The item already contains the attribute " + args[3] + "at the equipment slot " + equipmentSlot + ".");
            player.sendMessage(ChatColor.RED + "To modify the attribute just write \"/itemsage attribute " + equipmentSlot + " " + args[3] + "\".");
            return false;
        }
        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(),"test", 1, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.GREEN + "The attribute " + attribute + " has been added to the item.");
        return true;
    }

    private static boolean args5(Player player, String[] args, ItemStack mainHandItem) {
        player.sendMessage(ChatColor.RED + "You reached args 5!");
        return false;
    }

}