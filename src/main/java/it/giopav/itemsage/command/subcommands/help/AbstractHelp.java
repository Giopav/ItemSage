package it.giopav.itemsage.command.subcommands.help;

import it.giopav.itemsage.command.superclasses.AbstractCommand;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class AbstractHelp extends AbstractCommand {
    protected final HashMap<String, String> messageMap = new HashMap<>();

    protected AbstractHelp() {
        super(argsArray[10]);

        messageMap.put(argsArray[0], ChatColor.GREEN + "Amount: /itemsage help amount");
        messageMap.put(argsArray[1], ChatColor.GREEN + "Attribute: /itemsage help attribute");
        messageMap.put(argsArray[2], ChatColor.GREEN + "Data: /itemsage help data");
        messageMap.put(argsArray[3], ChatColor.GREEN + "Durability: /itemsage help durability");
        messageMap.put(argsArray[4], ChatColor.GREEN + "Enchant: /itemsage help enchant");
        messageMap.put(argsArray[5], ChatColor.GREEN + "Flag: /itemsage help flag");
        messageMap.put(argsArray[6], ChatColor.GREEN + "Lore: /itemsage help lore");
        messageMap.put(argsArray[7], ChatColor.GREEN + "Material: /itemsage help material");
        messageMap.put(argsArray[8], ChatColor.GREEN + "Name: /itemsage help name");
        messageMap.put(argsArray[9], ChatColor.GREEN + "Unbreakable: /itemsage help unbreakable");
    }
}
