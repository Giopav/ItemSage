package it.giopav.itemsage.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.regex.Pattern;

public class StringUtils {

    public static Component deserializeRightString(String string) {
        Component component;
        if (Pattern.matches(".*[&§][0-9a-fk-or].*", string)) {
            component = LegacyComponentSerializer.legacyAmpersand().deserialize(string);
        } else {
            component = MiniMessage.miniMessage().deserialize(string);
        }
        return component;
    }

    public static String serializeRightString(Component component) {
        String componentString = PlainTextComponentSerializer.plainText().serialize(component);
        String string;
        if (Pattern.matches(".*[&§][0-9a-fk-or].*", componentString)) {
            string = LegacyComponentSerializer.legacyAmpersand().serialize(component);
        } else {
            string = MiniMessage.miniMessage().serialize(component);
        }
        return string;
    }

    public static String userFriendlyString(String string) {
        StringBuilder userFriendly = new StringBuilder();
        for (String substring : string.split(" ")) {
            userFriendly
                    .append(substring.substring(0, 1).toUpperCase())
                    .append(substring.substring(1))
                    .append(" ");
        }
        return userFriendly.toString().trim();
    }

}
