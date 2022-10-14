package it.giopav.itemsage.command;


import it.giopav.itemsage.command.tabcomplete.*;
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

public class TabCompleteHandler implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return null;
        }
        Player player = (Player) sender;
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
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
                    completions = TBName.name(mainHandItem, args);
                    break;
                case "lore":
                    completions = TBLore.lore(mainHandItem, args);
                    break;
                case "enchant":
                    completions = TBEnchant.enchant(mainHandItem, args);
                    break;
                case "attribute":
                    completions = TBAttribute.attribute(mainHandItem, args);
                    break;
                case "flag":
                    completions = TBFlag.flag(mainHandItem, args);
                    break;
                case "amount":
                    completions = TBAmount.amount(mainHandItem, args);
                    break;
                case "material":
                    completions = TBMaterial.material(mainHandItem, args);
                    break;
                default:
            }
            return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
        }
        return null;
    }
}
