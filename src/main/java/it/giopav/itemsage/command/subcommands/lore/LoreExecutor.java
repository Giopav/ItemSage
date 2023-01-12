package it.giopav.itemsage.command.subcommands.lore;

import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import it.giopav.itemsage.command.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class LoreExecutor extends AbstractLore implements ExecutorInterface {
    @Override
    public boolean command(Player player, String[] args) {
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        if (mainHandItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You have to hold an item in your main hand.");
            return false;
        }

        if (args.length == 1) {
            return sendLore(player, mainHandItem);
        } else if (args.length == 2) {
            return sendLine(player, args, mainHandItem);
        } else if (args.length >= 3) {
            return redirectAddOrEdit(player, args, mainHandItem);
        }

        player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
        return false;
    }

    private boolean sendLore(Player player, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasLore()) {
            player.sendMessage(ChatColor.RED + "This item doesn't have a lore.");
            return false;
        }

        List<Component> lore = mainHandItem.getItemMeta().lore();
        assert lore != null;
        player.sendMessage(ChatColor.GREEN + "The lore is:");
        for (int i = 0; i < lore.size(); i++) {
            player.sendMessage(Component.text(ChatColor.GRAY + String.valueOf(i+1) + ") " + ChatColor.RESET)
                    .append(lineMessage(lore.get(i))));
        }
        return true;
    }

    private boolean sendLine(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            player.sendMessage(ChatColor.RED + "You have to enter a line to add.");
            return false;
        }
        if (!mainHandItem.getItemMeta().hasLore()) {
            player.sendMessage(ChatColor.RED + "This item doesn't have a lore.");
            return false;
        }
        if (!Pattern.matches("\\d+", args[1])) {
            player.sendMessage(ChatColor.RED + "You have to enter a valid lore line.");
            return false;
        }
        int lineIndex = Integer.parseInt(args[1]);
        if (lineIndex == 0) {
            player.sendMessage(ChatColor.RED + "The first valid lore line is 1.");
            return false;
        }
        List<Component> lore = mainHandItem.getItemMeta().lore();
        assert lore != null;
        if (lore.size() <= lineIndex-1) {
            player.sendMessage(ChatColor.RED + "This item doesn't reach line " + lineIndex + ".");
            return false;
        }

        player.sendMessage(ChatColor.GREEN + "The selected line is:");
        Component line = lore.get(lineIndex-1);
        player.sendMessage(lineMessage(line));
        return true;
    }

    private boolean redirectAddOrEdit(Player player, String[] args, ItemStack mainHandItem) {
        if (args[1].equalsIgnoreCase("add")) {
            return addLine(player, args, mainHandItem);
        } else if (Pattern.matches("\\d+", args[1])) {
            return editLine(player, args, mainHandItem);
        } else {
            player.sendMessage(ChatColor.RED + "This command doesn't work like this.");
            return false;
        }
    }

    private boolean addLine(Player player, String[] args, ItemStack mainHandItem) {
        String lineString = stringFromArray(args);
        mainHandItem.setItemMeta(addLoreLine(mainHandItem.getItemMeta(), lineString));
        player.sendMessage(ChatColor.GREEN + "The lore line has been added:");
        player.sendMessage(lineMessage(StringUtils.deserializeRightString(lineString)));
        return true;
    }

    private boolean editLine(Player player, String[] args, ItemStack mainHandItem) {
        if (!mainHandItem.getItemMeta().hasLore()) {
            player.sendMessage(ChatColor.RED + "This item doesn't have a lore.");
            return false;
        }
        int lineIndex = Integer.parseInt(args[1]);
        if (lineIndex == 0) {
            player.sendMessage(ChatColor.RED + "The first valid lore line is 1.");
            return false;
        }
        if (Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size() <= lineIndex-1) {
            player.sendMessage(ChatColor.RED + "This item doesn't reach line " + lineIndex + ".");
            return false;
        }
        if (args[2].equalsIgnoreCase("remove") && args.length > 3) {
            player.sendMessage(ChatColor.RED + "You can't remove parts of the line, only the line in its entirety.");
            player.sendMessage(ChatColor.RED + "To do so, write \"/itemsage lore " + lineIndex + " remove\".");
            return false;
        }

        if (args[2].equalsIgnoreCase("remove")) {
            List<Component> lore = mainHandItem.getItemMeta().lore();
            assert lore != null;
            mainHandItem.setItemMeta(removeLoreLine(mainHandItem.getItemMeta(), lineIndex-1));
            player.sendMessage(ChatColor.GREEN + "The lore line " + lineIndex + " has been removed:");
            player.sendMessage(lineMessage(lore.get(lineIndex-1)));
        } else {
            String line = stringFromArray(args);
            mainHandItem.setItemMeta(setLoreLine(mainHandItem.getItemMeta(), line, lineIndex-1));
            player.sendMessage(ChatColor.GREEN + "The lore line " + lineIndex + " has been set to:");
            player.sendMessage(lineMessage(StringUtils.deserializeRightString(line)));
        }
        return true;
    }

    private ItemMeta removeLoreLine(ItemMeta itemMeta, int line) {
        List<Component> lore = itemMeta.lore();
        assert lore != null;
        lore.remove(line);
        itemMeta.lore(lore);
        return itemMeta;
    }

    private ItemMeta setLoreLine(ItemMeta itemMeta, String string, int line) {
        List<Component> lore = itemMeta.lore();
        assert lore != null;
        lore.set(line, StringUtils.deserializeRightString(string).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        return itemMeta;
    }

    private ItemMeta addLoreLine(ItemMeta itemMeta, String string) {
        List<Component> lore;
        if (itemMeta.hasLore()) {
            lore = itemMeta.lore();
        } else {
            lore = new ArrayList<>();
        }
        assert lore != null;
        lore.add(StringUtils.deserializeRightString(string).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(lore);
        return itemMeta;
    }

    private String stringFromArray(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < strings.length; i++) {
            stringBuilder.append(strings[i]).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private Component lineMessage(Component line) {
        String lineString = StringUtils.serializeRightString(line);
        return line
                .hoverEvent(Component.text(ChatColor.WHITE + "» Click to copy «"))
                .clickEvent(ClickEvent.copyToClipboard(lineString));
    }
}