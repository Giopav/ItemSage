package it.giopav.itemsage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.regex.Pattern;

public class Utils {
    public static Component deserializeRightString(String string) {
        Component component;
        if (Pattern.matches(".*&[0-9a-f].*", string)) {
            component = LegacyComponentSerializer.legacyAmpersand().deserialize(string);
        } else {
            component = MiniMessage.miniMessage().deserialize(string);
        }
        return component;
    }

    public static String serializeRightString(Component component) {
        String componentString = PlainTextComponentSerializer.plainText().serialize(component);
        String string;
        if (Pattern.matches(".*&[0-9a-f].*", componentString)) {
            string = LegacyComponentSerializer.legacyAmpersand().serialize(component);
        } else {
            string = MiniMessage.miniMessage().serialize(component);
        }
        return string;
    }
}
