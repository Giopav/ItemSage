package it.giopav.itemsage.command.helphandler;

import java.util.List;

public class HelpTabCompleter {

    public static void tabComplete(List<String> completions, String[] args) {
        if (args.length == 2) {
            completions.add("amount");
            completions.add("attribute");
            completions.add("data");
            completions.add("durability");
            completions.add("enchant");
            completions.add("flag");
            completions.add("lore");
            completions.add("material");
            completions.add("name");
            completions.add("unbreakable");
        }
    }

}
