package it.giopav.itemsage.command.helphandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpExecutor {

    public static boolean command(Player player, String[] args) {
        if (args.length == 1) {
            return sendGeneral(player);
        } else if (args.length == 2) {
            return redirect(player, args[1]);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private static boolean sendGeneral(Player player) {
        player.sendMessage(ChatColor.GREEN + "General help menu:");
        player.sendMessage(ChatColor.GREEN + "Amount: /itemsage help amount");
        player.sendMessage(ChatColor.GREEN + "Attribute: /itemsage help attribute");
        player.sendMessage(ChatColor.GREEN + "Data: /itemsage help data");
        player.sendMessage(ChatColor.GREEN + "Durability: /itemsage help durability");
        player.sendMessage(ChatColor.GREEN + "Enchant: /itemsage help enchant");
        player.sendMessage(ChatColor.GREEN + "Flag: /itemsage help flag");
        player.sendMessage(ChatColor.GREEN + "Lore: /itemsage help lore");
        player.sendMessage(ChatColor.GREEN + "Material: /itemsage help material");
        player.sendMessage(ChatColor.GREEN + "Name: /itemsage help name");
        player.sendMessage(ChatColor.GREEN + "Unbreakable: /itemsage help unbreakable");
        return true;
    }

    private static boolean redirect(Player player, String option) {
        option = option.toLowerCase();
        switch (option) {
            case "amount":
                return sendAmount(player);
            case "attribute":
                return sendAttribute(player);
            case "data":
                return sendData(player);
            case "durability":
                return sendDurability(player);
            case "enchant":
                return sendEnchant(player);
            case "flag":
                return sendFlag(player);
            case "lore":
                return sendLore(player);
            case "material":
                return sendMaterial(player);
            case "name":
                return sendName(player);
            case "unbreakable":
                return sendUnbreakable(player);
            default:
                player.sendMessage(ChatColor.RED + "I don't recognize the argument \"" + option + "\".");
                return false;
        }
    }

    private static boolean sendAmount(Player player) {
        player.sendMessage(ChatColor.GREEN + "Amount help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage amount  | Display the amount.");
        player.sendMessage(ChatColor.GREEN + "/itemsage amount set [amount] | Set the amount to [amount].");
        return true;
    }

    private static boolean sendAttribute(Player player) {
        player.sendMessage(ChatColor.GREEN + "Attribute help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute | Display all of the attributes.");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] | Display all the attributes of the [equipment slot].");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] [attribute] | Display the [attribute] of the [equipment slot].");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] [attribute] [modifier] | Sets the [modifier] to the [attribute] of the [equipment slot], you can't modify the operation.");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] [attribute] remove | Remove the [attribute] of the [equipment slot].");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] add [attribute] | Adds the [attribute] with the default modifier (1) and default operation (ADD_NUMBER) to the [equipment slot].");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] add [attribute] [modifier] | Adds the [attribute] with the corresponding [modifier] and default operation (ADD_NUMBER) to the [equipment slot].");
        player.sendMessage(ChatColor.GREEN + "/itemsage attribute [equipment slot] add [attribute] [modifier] [operation] | Adds the [attribute] with the corresponding [modifier] and [operation] to the [equipment slot].");
        return true;
    }

    private static boolean sendData(Player player) {
        player.sendMessage(ChatColor.GREEN + "Data help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage data | Display the data key(s).");
        player.sendMessage(ChatColor.GREEN + "/itemsage data remove [key] | Remove the [key] from the data.");
        return true;
    }

    private static boolean sendDurability(Player player) {
        player.sendMessage(ChatColor.GREEN + "Durability help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage durability | Display the durability.");
        player.sendMessage(ChatColor.GREEN + "/itemsage durability set [durability] | Set the durability to [durability].");
        return true;
    }

    private static boolean sendEnchant(Player player) {
        player.sendMessage(ChatColor.GREEN + "Enchant help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant | Display the enchantment(s).");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant [enchantment] | Display the [enchantment].");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant [enchantment] [level] | Set the level of the [enchantment] as [level].");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant [enchantment] remove | Remove the [enchantment].");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant add [enchantment] | Add the [enchantment] with the first level.");
        player.sendMessage(ChatColor.GREEN + "/itemsage enchant add [enchantment] [level] | Add the [enchantment] with the [level].");
        return true;
    }

    private static boolean sendFlag(Player player) {
        player.sendMessage(ChatColor.GREEN + "Flag help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage flag | Display the flag(s).");
        player.sendMessage(ChatColor.GREEN + "/itemsage add [flag] | Add the [flag].");
        player.sendMessage(ChatColor.GREEN + "/itemsage [flag] remove | Remove the [flag].");
        return true;
    }

    private static boolean sendLore(Player player) {
        player.sendMessage(ChatColor.GREEN + "Lore help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage lore | Display the lore.");
        player.sendMessage(ChatColor.GREEN + "/itemsage lore [line] | Display the [line].");
        player.sendMessage(ChatColor.GREEN + "/itemsage lore [line] [lore] | Set the [line]'s text to [lore].");
        player.sendMessage(ChatColor.GREEN + "/itemsage lore [line] remove | Remove the [line], all of the following lines get bumped up once.");
        player.sendMessage(ChatColor.GREEN + "/itemsage lore add [text] | Add the [text] as the last line.");
        return true;
    }

    private static boolean sendMaterial(Player player) {
        player.sendMessage(ChatColor.GREEN + "Material help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage material | Display the material.");
        player.sendMessage(ChatColor.GREEN + "/itemsage material set [material] | Set the material to [material].");
        return true;
    }

    private static boolean sendName(Player player) {
        player.sendMessage(ChatColor.GREEN + "Name help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage name | Display the name.");
        player.sendMessage(ChatColor.GREEN + "/itemsage name set [name] | Set the name to [name].");
        return true;
    }

    private static boolean sendUnbreakable(Player player) {
        player.sendMessage(ChatColor.GREEN + "Unbreakable help menu:");
        player.sendMessage(ChatColor.GREEN + "/itemsage unbreakable | Toggle unbreakable (false to true, true to false).");
        player.sendMessage(ChatColor.GREEN + "/itemsage unbreakable true | Set unbreakable to true.");
        player.sendMessage(ChatColor.GREEN + "/itemsage unbreakable false | Set unbreakable to false.");
        return true;
    }

}
