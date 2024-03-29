package it.giopav.itemsage.command.subcommands.material;

import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MaterialExecutor extends AbstractMaterial implements ExecutorInterface {
    @Override
    public boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendMaterial(player, mainHandItem);
        } else if (args.length == 3) {
            return setMaterial(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private boolean sendMaterial(Player player, ItemStack mainHandItem) {
        Material material = mainHandItem.getType();
        player.sendMessage(ChatColor.GREEN + "This item's material is:");
        player.sendMessage(materialMessage(material));
        return true;
    }

    private boolean setMaterial(Player player, String[] args, ItemStack mainHandItem) {
        if (!args[1].equalsIgnoreCase("set")) {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
        String materialString = args[2].toUpperCase().replaceFirst("MINECRAFT:", "");
        if (Material.getMaterial(materialString) == null) {
            player.sendMessage(ChatColor.RED + "You have to input a valid material.");
            return false;
        }

        Material material = Material.getMaterial(materialString);
        assert material != null;
        mainHandItem.setType(material);
        player.sendMessage(ChatColor.GREEN + "This item's material has been set to:");
        player.sendMessage(materialMessage(material));
        return true;
    }

    private Component materialMessage(Material material) {
        String materialString = material.toString().toUpperCase().replaceFirst("MINECRAFT:", "");
        return Component.text(materialString)
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(materialString));
    }
}