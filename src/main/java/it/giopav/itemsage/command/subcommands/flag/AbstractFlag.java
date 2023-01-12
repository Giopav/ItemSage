package it.giopav.itemsage.command.subcommands.flag;

import it.giopav.itemsage.command.superclasses.AbstractCommand;
import org.bukkit.inventory.ItemFlag;

public class AbstractFlag extends AbstractCommand {
    public AbstractFlag() {
        super(argsArray[5]);
    }

    public static ItemFlag getItemFlagValue(String string) {
        ItemFlag itemFlag = null;
        for (ItemFlag itemFlagElement : ItemFlag.values()) {
            if (itemFlagElement.toString().equals(string)) {
                itemFlag = itemFlagElement;
                break;
            }
        }
        return itemFlag;
    }
}