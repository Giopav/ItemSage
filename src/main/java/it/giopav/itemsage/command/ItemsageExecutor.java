package it.giopav.itemsage.command;

import it.giopav.itemsage.command.amounthandler.AmountExecutor;
import it.giopav.itemsage.command.attributehandler.AttributeExecutor;
import it.giopav.itemsage.command.datahandler.DataExecutor;
import it.giopav.itemsage.command.durabilityhandler.DurabilityExecutor;
import it.giopav.itemsage.command.enchanthandler.EnchantExecutor;
import it.giopav.itemsage.command.flaghandler.FlagExecutor;
import it.giopav.itemsage.command.helphandler.HelpExecutor;
import it.giopav.itemsage.command.lorehandler.LoreExecutor;
import it.giopav.itemsage.command.materialhandler.MaterialExecutor;
import it.giopav.itemsage.command.namehandler.NameExecutor;
import it.giopav.itemsage.command.unbreakablehandler.UnbreakableExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ItemsageExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return false;
        }

        Player player = (Player) sender;
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "amount":
                    return AmountExecutor.command(player, args);
                case "attribute":
                    return AttributeExecutor.command(player, args);
                case "data":
                    return DataExecutor.command(player, args);
                case "durability":
                    return DurabilityExecutor.command(player, args);
                case "enchant":
                    return EnchantExecutor.command(player, args);
                case "flag":
                    return FlagExecutor.command(player, args);
                case "help":
                    return HelpExecutor.command(player, args);
                case "lore":
                    return LoreExecutor.command(player, args);
                case "material":
                    return MaterialExecutor.command(player, args);
                case "name":
                    return NameExecutor.command(player, args);
                case "unbreakable":
                    return UnbreakableExecutor.command(player, args);
                default:
                    player.sendMessage(ChatColor.RED + "I don't recognize the argument.");
                    return false;
            }
        }
        return false;
    }

}
