package it.giopav.itemsage.command.enchanthandler;

import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.regex.Pattern;

public class EnchantExecutor {

    public static boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendAllEnchantments(player, mainHandItem);
        } else if (args.length == 2) {
            return sendEnchantment(player, args, mainHandItem);
        } else if (args.length == 3) {
            return redirectAddOrEdit(player, args, mainHandItem);
        } else if (args.length == 4) {
            return addEnchantAndLevel(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendAllEnchantments(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasEnchants()) {
            player.sendMessage(ChatColor.RED + "This item is not enchanted.");
            return false;
        }

        Map<Enchantment, Integer> enchantments = mainHandItem.getEnchantments();
        if (enchantments.size() == 1) {
            player.sendMessage(ChatColor.GREEN + "The enchantment is:");
        } else {
            player.sendMessage(ChatColor.GREEN + "The enchantments are:");
        }
        for (int i = 0; i < enchantments.size(); i++) {
            Enchantment enchantment = (Enchantment) enchantments.keySet().toArray()[i];
            player.sendMessage(Component.text(ChatColor.GRAY + String.valueOf(i+1) + ") " + ChatColor.AQUA)
                    .append(enchantMessage(enchantment, enchantments.get(enchantment))));
        }
        return true;
    }

    private static boolean sendEnchantment(Player player, String[] args, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasEnchants()) {
            player.sendMessage(ChatColor.RED + "This item is not enchanted.");
            return false;
        }
        Enchantment enchantment = Utils.getEnchantmentValue(args[1]);
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The entered enchantment does not exist.");
            return false;
        }
        if (!mainHandItem.getItemMeta().hasEnchant(enchantment)) {
            player.sendMessage(ChatColor.RED + "This item does not have the entered enchantment.");
            return false;
        }

        player.sendMessage(ChatColor.GREEN + "The enchantment is:");
        player.sendMessage(enchantMessage(enchantment, mainHandItem.getEnchantmentLevel(enchantment)));
        return true;
    }

    private static boolean redirectAddOrEdit(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return addEnchant(player, args, mainHandItem);
        } else {
            return editEnchant(player, args, mainHandItem);
        }
    }

    private static boolean addEnchant(Player player, String[] args, ItemStack mainHandItem) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[2].toLowerCase()));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[2].toUpperCase() + " does not exist.");
            return false;
        }

        mainHandItem.addUnsafeEnchantment(enchantment, enchantment.getStartLevel());
        player.sendMessage(ChatColor.GREEN + "The enchantment has been added to your item:");
        player.sendMessage(enchantMessage(enchantment, enchantment.getStartLevel()));
        return true;
    }

    private static boolean editEnchant(Player player, String[] args, ItemStack mainHandItem) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[1].toLowerCase()));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[1].toUpperCase() + " doesn't exist.");
            return false;
        }
        if (!mainHandItem.getItemMeta().getEnchants().containsKey(enchantment)) {
            player.sendMessage(ChatColor.RED + "The item does not have the enchant " + args[1].toUpperCase() + ".");
            return false;
        }

        int enchantmentLevel;
        if (args[2].equalsIgnoreCase("remove")) {
            enchantmentLevel = mainHandItem.getEnchantmentLevel(enchantment);
            mainHandItem.removeEnchantment(enchantment);
            player.sendMessage(ChatColor.GREEN + "The enchantment has been removed:");
        } else if (Pattern.matches("\\d+", args[2])) {
            enchantmentLevel = Integer.parseInt(args[2]);
            mainHandItem.addUnsafeEnchantment(enchantment, enchantmentLevel);
            player.sendMessage(ChatColor.GREEN + "The enchantment has been edited to:");
        } else {
            player.sendMessage(ChatColor.RED + "You either have to type \"remove\" or a level.");
            return false;
        }

        player.sendMessage(enchantMessage(enchantment, enchantmentLevel));
        return true;
    }

    private static boolean addEnchantAndLevel(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(args[2].toLowerCase()));
        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "The enchantment " + args[2].toUpperCase() + " doesn't exist.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[3])) {
            player.sendMessage(ChatColor.RED + "The level has to be an integer. (not " + args[3] + "!)");
            return false;
        }
        int enchantmentLevel = Integer.parseInt(args[3]);
        if (enchantmentLevel < enchantment.getStartLevel()) {
            player.sendMessage(ChatColor.RED + "The level can't be lower that " + enchantment.getStartLevel() + ".");
            return false;
        }
        if (enchantmentLevel > 100) {
            player.sendMessage(ChatColor.RED + "The level can't be higher that " + 100 + ".");
            return false;
        }

        mainHandItem.addUnsafeEnchantment(enchantment, enchantmentLevel);
        player.sendMessage(ChatColor.GREEN + "The enchantment has been added to your item:");
        player.sendMessage(enchantMessage(enchantment, enchantmentLevel));
        return true;
    }

    private static Component enchantMessage(Enchantment enchantment, int enchantmentLevel) {
        Component enchantmentDisplayName = enchantment.displayName(enchantmentLevel);
        String enchantmentKeyAndLevel = enchantment.getKey().toString().toUpperCase().replace("MINECRAFT:", "") + " " + enchantmentLevel;
        return Component.text()
                .append(enchantmentDisplayName
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(PlainTextComponentSerializer.plainText().serialize(enchantmentDisplayName))))
                .append(Component.text(" " + ChatColor.GRAY + "("))
                .append(Component.text(enchantmentKeyAndLevel)
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(enchantmentKeyAndLevel)))
                .append(Component.text(ChatColor.GRAY + ")"))
                .build();
    }

}