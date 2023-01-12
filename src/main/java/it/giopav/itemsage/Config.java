package it.giopav.itemsage;

import it.giopav.itemsage.command.subcommands.amount.AmountExecutor;
import it.giopav.itemsage.command.subcommands.amount.AmountTabCompleter;
import it.giopav.itemsage.command.subcommands.attribute.AttributeExecutor;
import it.giopav.itemsage.command.subcommands.attribute.AttributeTabCompleter;
import it.giopav.itemsage.command.subcommands.data.DataExecutor;
import it.giopav.itemsage.command.subcommands.data.DataTabCompleter;
import it.giopav.itemsage.command.subcommands.durability.DurabilityExecutor;
import it.giopav.itemsage.command.subcommands.durability.DurabilityTabCompleter;
import it.giopav.itemsage.command.subcommands.enchant.EnchantExecutor;
import it.giopav.itemsage.command.subcommands.enchant.EnchantTabCompleter;
import it.giopav.itemsage.command.subcommands.flag.FlagExecutor;
import it.giopav.itemsage.command.subcommands.flag.FlagTabCompleter;
import it.giopav.itemsage.command.subcommands.help.HelpExecutor;
import it.giopav.itemsage.command.subcommands.help.HelpTabCompleter;
import it.giopav.itemsage.command.subcommands.lore.LoreExecutor;
import it.giopav.itemsage.command.subcommands.lore.LoreTabCompleter;
import it.giopav.itemsage.command.subcommands.material.MaterialExecutor;
import it.giopav.itemsage.command.subcommands.material.MaterialTabCompleter;
import it.giopav.itemsage.command.subcommands.name.NameExecutor;
import it.giopav.itemsage.command.subcommands.name.NameTabCompleter;
import it.giopav.itemsage.command.superclasses.AbstractCommand;
import it.giopav.itemsage.command.superclasses.ExecutorInterface;
import it.giopav.itemsage.command.superclasses.TabCompleterInterface;
import it.giopav.itemsage.command.subcommands.unbreakable.UnbreakableExecutor;
import it.giopav.itemsage.command.subcommands.unbreakable.UnbreakableTabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {

    private static Config instance;

    private final ArrayList<String> enabledList = new ArrayList<>();
    private final HashMap<String, ExecutorInterface> executorMap = new HashMap<>();
    private final HashMap<String, TabCompleterInterface> tabCompleterMap = new HashMap<>();

    private Config() {

        String[] argsArray = AbstractCommand.getArgsArray();

        for (String command : argsArray) {
            if (isEnabled(command)) {
                enabledList.add(command);
            }
        }

        executorMap.put(argsArray[0], new AmountExecutor());
        executorMap.put(argsArray[1], new AttributeExecutor());
        executorMap.put(argsArray[2], new DataExecutor());
        executorMap.put(argsArray[3], new DurabilityExecutor());
        executorMap.put(argsArray[4], new EnchantExecutor());
        executorMap.put(argsArray[5], new FlagExecutor());
        executorMap.put(argsArray[6], new LoreExecutor());
        executorMap.put(argsArray[7], new MaterialExecutor());
        executorMap.put(argsArray[8], new NameExecutor());
        executorMap.put(argsArray[9], new UnbreakableExecutor());
        executorMap.put(argsArray[10], new HelpExecutor());

        tabCompleterMap.put(argsArray[0], new AmountTabCompleter());
        tabCompleterMap.put(argsArray[1], new AttributeTabCompleter());
        tabCompleterMap.put(argsArray[2], new DataTabCompleter());
        tabCompleterMap.put(argsArray[3], new DurabilityTabCompleter());
        tabCompleterMap.put(argsArray[4], new EnchantTabCompleter());
        tabCompleterMap.put(argsArray[5], new FlagTabCompleter());
        tabCompleterMap.put(argsArray[6], new LoreTabCompleter());
        tabCompleterMap.put(argsArray[7], new MaterialTabCompleter());
        tabCompleterMap.put(argsArray[8], new NameTabCompleter());
        tabCompleterMap.put(argsArray[9], new UnbreakableTabCompleter());
        tabCompleterMap.put(argsArray[10], new HelpTabCompleter());
    }

    private boolean isEnabled(String path) {
        return ItemSage.getInstance().getConfig().isConfigurationSection("command." + path) &&
                Objects.requireNonNull(ItemSage.getInstance().getConfig().getConfigurationSection("command." + path)).getBoolean("enabled");
    }

    public List<String> getEnabledList() {
        return enabledList;
    }

    public Map<String, ExecutorInterface> getExecutorMap() {
        return executorMap;
    }

    public Map<String, TabCompleterInterface> getTabCompleterMap() {
        return tabCompleterMap;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}