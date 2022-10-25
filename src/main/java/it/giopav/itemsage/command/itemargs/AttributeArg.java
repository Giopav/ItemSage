package it.giopav.itemsage.command.itemargs;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class AttributeArg {
    public static void attributeTabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasAttributeModifiers();
        if (args.length == 2) {
            if (condition) {
                for (Attribute attribute : Objects.requireNonNull(mainHandItem.getItemMeta().getAttributeModifiers()).keys()) {
                    completions.add(attribute.getKey().toString());
                }
            }
            completions.add("add");
        } else if (args.length == 3
                && condition
                && Objects.requireNonNull(mainHandItem.getItemMeta().getAttributeModifiers()).keys().contains(Attribute.valueOf(args[1]))) {
            completions.add("remove");
            for (AttributeModifier attributeModifier : Objects.requireNonNull(mainHandItem.getItemMeta().getAttributeModifiers()).get(Attribute.valueOf(args[1]))) {
                completions.add(String.valueOf(attributeModifier.getAmount()));
            }
        }
    }

    public static boolean attributeCommandExecutor(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 3) {
            return args3CommandExecutor(player, args, mainHandItem);
        } else if (args.length == 4) {
            return args4CommandExecutor(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args3CommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return args3AddCommandExecutor(player, args, mainHandItem);
        }
        args[1] = args[1].toUpperCase();
        Attribute attribute = Attribute.valueOf(args[1]);
        player.sendMessage(attribute.toString());
        if (mainHandItem.getItemMeta().getAttributeModifiers() == null) {
            player.sendMessage(ChatColor.RED + "This item does not have any attribute.");
            return false;
        }
        if (mainHandItem.getItemMeta().getAttributeModifiers().keySet().contains(attribute)) {
            player.sendMessage(ChatColor.RED + "The item does not contain the attribute " + attribute.getKey() + ".");
            return false;
        }
        if (args[2].equalsIgnoreCase("remove")) {
            ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
            mainHandItemMeta.removeAttributeModifier(attribute);
            mainHandItem.setItemMeta(mainHandItemMeta);
            player.sendMessage(ChatColor.GREEN + "The attribute " + attribute.getKey() + " has been removed.");
            return true;
        } else if (Pattern.matches("\\d+", args[2])) {
            AttributeModifier attributeModifier = new AttributeModifier("test", 1, AttributeModifier.Operation.ADD_NUMBER);
            ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
            mainHandItemMeta.addAttributeModifier(attribute, attributeModifier);
            mainHandItem.setItemMeta(mainHandItemMeta);
        }
        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean args3AddCommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        args[2] = args[2].toLowerCase();
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[2]));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[2] + " doesn't exist.");
            return false;
        }
        mainHandItem.addUnsafeEnchantment(enchantment, enchantment.getStartLevel());
        player.sendMessage(ChatColor.GREEN + "The enchantment " + enchantment.getKey() + " " + enchantment.getStartLevel() + " has been added to the item.");
        return true;
    }

    private static boolean args4CommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        return false;
    }
}