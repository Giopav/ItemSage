package it.giopav.itemsage.command;

import it.giopav.itemsage.command.itemargs.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return false;
        }
        Player player = (Player) sender;
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "help":
                    return Help.help(player);
                case "lore":
                    return LoreArg.loreCommandExecutor(player, args);
                case "enchant":
                    return EnchantArg.enchantCommandExecutor(player, args);
                case "attribute":
                    return AttributeArg.attributeCommandExecutor(player, args);
                case "flag":
                    //TODO
                    return true;
                case "name":
                    return NameArg.nameCommandExecutor(player, args);
                case "amount":
                    return AmountArg.amountCommandExecutor(player, args);
                case "material":
                    return MaterialArg.materialCommandExecutor(player, args);
                default:
                    player.sendMessage(ChatColor.RED + "I don't recognize the argument.");
                    return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            if (i == args.length-1) {
                break;
            } else if (args[i].equals("")) {
                return null;
            }
        }
        Player player = (Player) sender;
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("help");
            completions.add("name");
            completions.add("lore");
            completions.add("enchant");
            completions.add("attribute");
            completions.add("flag");
            completions.add("amount");
            completions.add("material");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        } else if (args.length > 1) {
            switch (args[0]) {
                case "name":
                    NameArg.nameTabComplete(completions, mainHandItem, args);
                    break;
                case "lore":
                    LoreArg.loreTabComplete(completions, mainHandItem, args);
                    break;
                case "enchant":
                    EnchantArg.enchantTabComplete(completions, mainHandItem, args);
                    break;
                case "attribute":
                    AttributeArg.attributeTabComplete(completions, mainHandItem, args);
                    break;
                case "flag":
                    FlagArg.flagTabComplete(completions, mainHandItem, args);
                    break;
                case "amount":
                    AmountArg.amountTabComplete(completions, mainHandItem, args);
                    break;
                case "material":
                    MaterialArg.materialTabComplete(completions, mainHandItem, args);
                    break;
                default:
            }
            return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
        }
        return null;
    }
}
