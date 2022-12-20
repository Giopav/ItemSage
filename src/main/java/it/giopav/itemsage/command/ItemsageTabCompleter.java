package it.giopav.itemsage.command;

import it.giopav.itemsage.command.amounthandler.AmountTabCompleter;
import it.giopav.itemsage.command.attributehandler.AttributeTabCompleter;
import it.giopav.itemsage.command.datahandler.DataTabCompleter;
import it.giopav.itemsage.command.durabilityhandler.DurabilityTabCompleter;
import it.giopav.itemsage.command.enchanthandler.EnchantTabCompleter;
import it.giopav.itemsage.command.flaghandler.FlagTabCompleter;
import it.giopav.itemsage.command.lorehandler.LoreTabCompleter;
import it.giopav.itemsage.command.materialhandler.MaterialTabCompleter;
import it.giopav.itemsage.command.namehandler.NameTabCompleter;
import it.giopav.itemsage.command.unbreakablehandler.UnbreakableTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemsageTabCompleter implements TabCompleter {

    // TabCompletes for args.length == 1 with help, name, lore, enchant, attribute, flag, amount, and material.
    // For args.length > 1 it calls the respective class and methods, if there is none it just returns null.
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return null;
        }
        for (int i = 0; i < args.length-1; i++) {
            if (args[i].equals("")) {
                return null;
            }
        }
        Player player = (Player) sender;
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        List<String> completions = new ArrayList<>();
        if (player.getEquipment().getItemInMainHand().getType().isAir()) {
            completions.add("help");
        } else if (args.length == 1) {
            completions.add("amount");
            completions.add("attribute");
            completions.add("data");
            completions.add("durability");
            completions.add("enchant");
            completions.add("flag");
            completions.add("help");
            completions.add("lore");
            completions.add("material");
            completions.add("name");
            completions.add("unbreakable");
        } else if (args.length > 1) {
            switch (args[0]) {
                case "amount":
                    AmountTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "attribute":
                    AttributeTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "data":
                    DataTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "durability":
                    DurabilityTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "enchant":
                    EnchantTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "flag":
                    FlagTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "lore":
                    LoreTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "material":
                    MaterialTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "name":
                    NameTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "unbreakable":
                    UnbreakableTabCompleter.tabComplete(completions, args);
                    break;
                default:
            }
        }
        return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
    }

}
