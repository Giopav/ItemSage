package it.giopav.itemsage.command.itemargs;

import it.giopav.itemsage.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoreArg {
    public static void loreTabComplete(List<String> completions, ItemStack mainHandItem, String[] args) {
        boolean condition = !mainHandItem.getType().isAir() && mainHandItem.getItemMeta().hasLore();
        if (args.length == 2) {
            if (condition) {
                for (int i = 0; i< Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size(); i++) {
                    completions.add(String.valueOf(i+1));
                }
            }
            completions.add("add");
        } else if (args.length == 3
                && condition
                && Pattern.matches("\\d+", args[1])
                && Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() > Integer.parseInt(args[1])-1) {
            completions.add("remove");
            completions.add(LegacyComponentSerializer.legacyAmpersand().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
            completions.add(MiniMessage.miniMessage().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
            completions.add(PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(mainHandItem.lore()).get(Integer.parseInt(args[1])-1)));
        }
    }

    public static boolean loreCommandExecutor(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 2) {
            return args2CommandExecutor(player, args, mainHandItem);
        } else if (args.length >= 3) {
            return args3OrMoreCommandExecutor(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    // Returns true or false, depending on whether the command succeeds or not.
    // Called if the arguments are 2.
    private static boolean args2CommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "You have to enter a line to add.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[1])) {
            player.sendMessage(ChatColor.RED + "You have to enter a valid lore line.");
            return false;
        }
        player.sendMessage(ChatColor.GREEN + "The selected lore is "
                + ChatColor.RESET + Objects.requireNonNull(mainHandItem.getItemMeta().lore()).get(Integer.parseInt(args[1]))
                + ChatColor.GREEN + ".");
        return true;
    }

    // Returns true or false, depending on whether the command succeeds or not.
    // Called if the arguments are 3 or more.
    private static boolean args3OrMoreCommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return args3AddCommandExecutor(player, args, mainHandItem);
        } else if (Pattern.matches("\\d+", args[1])) {
            return args3LineCommandExecutor(player, args, mainHandItem);
        } else {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
    }

    // Returns true or false, depending on whether the command succeeds or not.
    // Called if the arguments are 3 or more and the second arg is "add".
    private static boolean args3AddCommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        mainHandItem.setItemMeta(addLoreLine(mainHandItem.getItemMeta(), stringFromArray(args)));
        player.sendMessage(ChatColor.GREEN + "The lore has been added.");
        return true;
    }

    // Returns true or false, depending on whether the command succeeds or not.
    // Called if the arguments are 3 or more and the second arg is a line (int).
    private static boolean args3LineCommandExecutor(Player player, String[] args, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasLore()) {
            player.sendMessage(ChatColor.RED + "This item does not have a lore.");
            return false;
        }
        if (Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() <= Integer.parseInt(args[1])-1) {
            player.sendMessage(ChatColor.RED + "This item does not reach line " + args[1] + ".");
            return false;
        }
        if (args[2].equalsIgnoreCase("remove") && args.length > 3) {
            player.sendMessage(ChatColor.RED + "You can't remove parts of the line, only the line in its entirety.");
            player.sendMessage(ChatColor.RED + "To do so, write \"/itemsage lore " + args[1] + " remove\".");
            return false;
        }
        if (args[2].equalsIgnoreCase("remove")) {
            mainHandItem.setItemMeta(removeLoreLine(mainHandItem.getItemMeta(), Integer.parseInt(args[1])-1));
            player.sendMessage(ChatColor.GREEN + "The lore has been removed.");
            return true;
        }
        mainHandItem.setItemMeta(setLoreLine(mainHandItem.getItemMeta(), Integer.parseInt(args[1])-1, stringFromArray(args)));
        player.sendMessage(ChatColor.GREEN + "The lore has been set.");
        return true;
    }

    // Returns the ItemMeta with the line (of the lore) at the given position removed.
    private static ItemMeta removeLoreLine(ItemMeta itemMeta, int line) {
        List<Component> lore = itemMeta.lore();
        assert lore != null;
        lore.remove(line);
        itemMeta.lore(lore);
        return itemMeta;
    }

    // Returns the ItemMeta with the string set at the position (of the lore) given.
    private static ItemMeta setLoreLine(ItemMeta itemMeta, int line, String string) {
        List<Component> lore = itemMeta.lore();
        assert lore != null;
        lore.set(line, Utils.deserializeRightString(string).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        return itemMeta;
    }
    
    // Returns the ItemMeta with the string added to the end (of the lore).
    private static ItemMeta addLoreLine(ItemMeta itemMeta, String string) {
        List<Component> lore;
        if (itemMeta.hasLore()) {
            lore = itemMeta.lore();
        } else {
            lore = new ArrayList<>();
        }
        assert lore != null;
        lore.add(Utils.deserializeRightString(string).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        return itemMeta;
    }

    // Only used for the command arguments.
    // Returns the input array as a string connected by spaces (and trimmed at the end ofc).
    // -> IGNORES THE FIRST 2 ELEMENTS <-
    // This is because the first two elements are always the arguments of the command, not the line.
    private static String stringFromArray(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i<strings.length; i++) {
            stringBuilder.append(strings[i]).append(" ");
        }
        return stringBuilder.toString().trim();
    }
}
