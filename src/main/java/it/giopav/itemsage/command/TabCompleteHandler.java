package it.giopav.itemsage.command;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabCompleteHandler implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "The command can only be executed via game.");
            return null;
        }
        Player player = (Player) sender;
        ItemStack mainHandItem = player.getEquipment().getItemInMainHand();
        player.sendMessage(mainHandItem.toString());
        boolean itemStackExists = !mainHandItem.getType().isAir();
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("amount");
            completions.add("attribute");
            completions.add("enchant");
            completions.add("flag");
            completions.add("lore");
            completions.add("material");
            completions.add("name");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        } else if (args.length == 2) {
            switch (args[0]) {
                case "amount": //single value
                    if (itemStackExists) {
                        completions.add(String.valueOf(mainHandItem.getAmount()));
                    }
                    break;
                case "attribute": //array value
                    completions.add("add");
                    completions.add("remove");
                    if (itemStackExists && mainHandItem.getItemMeta().hasAttributeModifiers()) {
                        for (Attribute attribute : Objects.requireNonNull(mainHandItem.getItemMeta().getAttributeModifiers()).keys()) {
                            completions.add(attribute.toString());
                        }
                    }
                    break;
                case "enchant": //array value
                    completions.add("add");
                    completions.add("remove");
                    if (itemStackExists && mainHandItem.getItemMeta().hasEnchants()) {
                        for (Enchantment enchantment : mainHandItem.getItemMeta().getEnchants().keySet()) {
                            completions.add(enchantment.toString());
                        }
                    }
                    break;
                case "flag": //array value
                    completions.add("add");
                    completions.add("remove");
                    if (itemStackExists && !mainHandItem.getItemFlags().isEmpty()) {
                        for (ItemFlag itemFlag : Objects.requireNonNull(mainHandItem.getItemFlags())) {
                            completions.add(itemFlag.toString());
                        }
                    }
                    break;
                case "lore": //array value
                    completions.add("add");
                    completions.add("append");
                    completions.add("remove");
                    if (itemStackExists && mainHandItem.getItemMeta().hasLore()) {
                        for (int i = 0; i< Objects.requireNonNull(mainHandItem.getItemMeta().lore()).size(); i++) {
                            completions.add(String.valueOf(i+1));
                        }
                    }
                    break;
                case "material": //single value
                    if (itemStackExists) {
                        completions.add(mainHandItem.getType().toString());
                    }
                    break;
                case "name": //single value
                    if (itemStackExists && mainHandItem.getItemMeta().hasDisplayName()) {
                        completions.add(mainHandItem.displayName().toString().replace("§", "&"));
                    }
                    completions.add("remove");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + args[0]);
            }
            return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
        }
        return null;
    }
}
