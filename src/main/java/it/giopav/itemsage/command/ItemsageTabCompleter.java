package it.giopav.itemsage.command;

import it.giopav.itemsage.command.amounthandler.AmountTabCompleter;
import it.giopav.itemsage.command.attributehandler.AttributeTabCompleter;
import it.giopav.itemsage.command.enchanthandler.EnchantTabCompleter;
import it.giopav.itemsage.command.flaghandler.FlagTabCompleter;
import it.giopav.itemsage.command.lorehandler.LoreTabCompleter;
import it.giopav.itemsage.command.materialhandler.MaterialTabCompleter;
import it.giopav.itemsage.command.namehandler.NameTabCompleter;
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
                    NameTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "lore":
                    LoreTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "enchant":
                    EnchantTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "attribute":
                    AttributeTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "flag":
                    FlagTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "amount":
                    AmountTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                case "material":
                    MaterialTabCompleter.tabComplete(completions, mainHandItem, args);
                    break;
                default:
            }
            return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
        }
        return null;
    }

}
