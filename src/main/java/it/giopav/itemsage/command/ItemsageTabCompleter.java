package it.giopav.itemsage.command;

import it.giopav.itemsage.Config;
import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
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
import java.util.Map;

public class ItemsageTabCompleter implements TabCompleter {
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
        return redirect(player, args);
    }

    public List<String> redirect(Player player, String[] args) {
        List<String> completions = new ArrayList<>();
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        Map<String, TabCompleterInterface> tabCompleterMap = Config.getInstance().getTabCompleterMap();
        List<String> enabledList = Config.getInstance().getEnabledList();
        if (player.getEquipment().getItemInMainHand().getType().isAir()) {
            completions.add("help");
        } else if (args.length == 1) {
            completions.addAll(enabledList);
        } else if (args.length > 1 && enabledList.contains(args[0])) {
            tabCompleterMap.get(args[0]).tabComplete(completions, mainHandItem, args);
        }
        return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
    }
}