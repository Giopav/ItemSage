package it.giopav.itemsage.command.flaghandler;

import org.bukkit.inventory.ItemFlag;

public class FlagHelper {

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
