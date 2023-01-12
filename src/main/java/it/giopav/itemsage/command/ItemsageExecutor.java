package it.giopav.itemsage.command;

import it.giopav.itemsage.Config;
import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ItemsageExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return false;
        }
        if (args.length == 0) {
            return false;
        }
        return redirect((Player) sender, args);
    }

    private boolean redirect(Player player, String[] args) {
        args[0] = args[0].toLowerCase();
        Map<String, ExecutorInterface> executorMap = Config.getInstance().getExecutorMap();
        return Config.getInstance().getEnabledList().contains(args[0])
                && executorMap.containsKey(args[0])
                && executorMap.get(args[0]).command(player, args);
    }
}