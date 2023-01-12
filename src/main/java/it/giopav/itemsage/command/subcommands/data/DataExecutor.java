package it.giopav.itemsage.command.subcommands.data;

import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class DataExecutor extends AbstractData implements ExecutorInterface {
    @Override
    public boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendData(player, mainHandItem);
        } else if (args.length == 3) {
            return removeData(player, mainHandItem, args);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private boolean sendData(Player player, ItemStack mainHandItem) {
        PersistentDataContainer dataContainer = mainHandItem.getItemMeta().getPersistentDataContainer();
        if (dataContainer.isEmpty()) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain any data!");
            return false;
        }

        player.sendMessage(ChatColor.GREEN + "This is the data inside this item:");
        for (NamespacedKey key : dataContainer.getKeys()) {
            player.sendMessage( Component.text(ChatColor.GRAY + "» ").append(dataMessage(key)));
        }
        return true;
    }

    private boolean removeData(Player player, ItemStack mainHandItem, String[] args) {
        ItemMeta mainHandItemMeta = mainHandItem.getItemMeta();
        PersistentDataContainer dataContainer = mainHandItemMeta.getPersistentDataContainer();
        if (NamespacedKey.fromString(args[2]) == null) {
            player.sendMessage(ChatColor.RED + "The argument \"" + args[2] + "\" is not a valid key!");
            return false;
        }
        NamespacedKey key = NamespacedKey.fromString(args[2]);
        assert key != null;
        if (!dataContainer.has(key)) {
            player.sendMessage(ChatColor.RED + "This item doesn't contain the key \"" + key + "\"!");
            return false;
        }

        dataContainer.remove(key);
        mainHandItem.setItemMeta(mainHandItemMeta);
        player.sendMessage(ChatColor.RED + "The following key has been removed from this item:");
        player.sendMessage(dataMessage(key));
        return true;
    }

    private Component dataMessage(NamespacedKey key) {
        return Component.text(key.toString())
                        .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                        .clickEvent(ClickEvent.copyToClipboard(key.toString()));
    }
}
