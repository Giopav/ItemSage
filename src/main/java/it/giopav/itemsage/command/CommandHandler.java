package it.giopav.itemsage.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {
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
                    return Help.help(player, args);
                case "name":
                    return Name.name(player, args);
                default:
            }
        }
        return false;
    }
}
