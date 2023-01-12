package it.giopav.itemsage.command.superclasses;

public abstract class AbstractCommand {
    protected static final String[] argsArray = {"amount", "attribute", "data", "durability", "enchant",
            "flag", "lore", "material", "name", "unbreakable", "help"};

    protected final String path;

    protected AbstractCommand(String path) {
        this.path = path.toLowerCase();
    }

    public static String[] getArgsArray() {
        return argsArray;
    }
}
