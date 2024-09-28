package de.frinshhd.logicsuite.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.RegExp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormat {

    public static String MINECRAFT_TRANSLATABLE = "minecraftTranslatable_";

    public static String build(String message) {
        return buildPlayer(null, message);
    }

    public static String buildPlayer(Player player, String message) {
        message = buildHex(message);

        Pattern pattern = Pattern.compile("\\{[^}]*\\}");
        Matcher matcher = pattern.matcher(message);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String matched = matcher.group();
            String translated = Translator.build(matched.substring(1, matched.length() - 1));

            translated = buildHex(translated);

            matcher.appendReplacement(result, translated);
        }
        matcher.appendTail(result);

        // Remove curly brackets from the result string
        String finalResult = result.toString().replace("{", "").replace("}", "");

        return ChatColor.translateAlternateColorCodes('&', finalResult);
    }


    public static String buildHex(String message) {
        Pattern pattern = Pattern.compile("&#([0-9A-Fa-f]{6})");
        Matcher matcher = pattern.matcher(message);

        StringBuilder result = new StringBuilder();
        int lastEnd = 0;

        while (matcher.find()) {
            // Append text before the match
            result.append(message, lastEnd, matcher.start());

            // Extract the hex color code
            String hexCode = matcher.group(1);

            // Convert hex code to Minecraft color code format
            StringBuilder colorCode = new StringBuilder("§x");
            for (char c : hexCode.toCharArray()) {
                colorCode.append("§").append(c);
            }

            // Append the converted color code
            result.append(colorCode.toString());

            lastEnd = matcher.end();
        }

        // Append any remaining text after the last match
        result.append(message.substring(lastEnd));

        return result.toString();
    }


    //--------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------

    // Convert a hex color code to Minecraft's TextColor
    public static TextColor hexToTextColor(String hex) {
        // Remove the # symbol before parsing
        return TextColor.color(Integer.parseInt(hex.substring(2), 16));
    }

    // Build a Component by applying hex colors to the text
    public static Component buildColoredComponent(Player player, String message) {
        {
            Pattern pattern = Pattern.compile("\\{[^}]*\\}");
            Matcher matcher = pattern.matcher(message);
            StringBuilder result = new StringBuilder();

            while (matcher.find()) {
                String matched = matcher.group();
                String translated = Translator.build(matched.substring(1, matched.length() - 1));
                matcher.appendReplacement(result, translated);
            }
            matcher.appendTail(result);

            // Remove curly brackets from the result string
            message = result.toString().replace("{", "").replace("}", "");

        }

        message = ChatColor.translateAlternateColorCodes('&', message); // Translate color codes

        // Pattern to find hex color codes in the format &#RRGGBB
        Pattern pattern = Pattern.compile("&#([0-9A-Fa-f]{6})");
        Matcher matcher = pattern.matcher(message);

        Component result = Component.empty(); // Start with an empty Component
        int lastEnd = 0; // Track the end of the last match

        while (matcher.find()) {
            // Text before the current color code
            if (matcher.start() > lastEnd) {
                String textBeforeColor = message.substring(lastEnd, matcher.start());
                result = result.append(Component.text(textBeforeColor)); // Append uncolored text
            }

            // Get the current color code and remove it from the text
            String hexCode = matcher.group();
            TextColor color = hexToTextColor(hexCode);

            // Move the lastEnd to after the current hex code
            lastEnd = matcher.end();

            // Check if there is more text after this color code
            int nextColorStart = matcher.find() ? matcher.start() : message.length();

            // Text that should be colored with the current color
            String coloredText = message.substring(lastEnd, nextColorStart);

            // Append the colored text to the result
            result = result.append(Component.text(coloredText, color));

            lastEnd = nextColorStart;
            matcher.region(nextColorStart, message.length()); // Update the region to start from the last processed text
        }

        // Append any remaining uncolored text after the last color code
        if (lastEnd < message.length()) {
            String remainingText = message.substring(lastEnd);
            result = result.append(Component.text(remainingText));
        }

        return result;
    }

    public static Component buildComponent(String message) {
        return buildPlayerComponent(null, message);
    }

    // Build the final component with hex colors and placeholders
    public static Component buildPlayerComponent(Player player, String message) {
        // Apply hex colors to the message
        Component component = buildColoredComponent(player, message);

        if (player != null) {

            // Replace placeholders with actual values
            String playerNamePlaceholder = "{player}";
            Component playerNameComponent = Component.text(player.getName());

            // Replace the placeholder in the component
            component = component.replaceText(builder -> builder.matchLiteral(playerNamePlaceholder).replacement(playerNameComponent));
        }

        Pattern patternPlaceholders = Pattern.compile("\\%[^}]*\\%");
        Matcher matcherPlaceholders = patternPlaceholders.matcher(message);

        while (matcherPlaceholders.find()) {
            @RegExp String matched = matcherPlaceholders.group();
            String placeholder = matched.substring(1, matched.length() - 1);

            if (placeholder.startsWith(MINECRAFT_TRANSLATABLE)) {
                component = component.replaceText(TextReplacementConfig.builder().match(matched).replacement(Component.translatable(placeholder.substring(MINECRAFT_TRANSLATABLE.length()))).build());
            }
        }

        return component;
    }

    public static String buildMinecraftTranslatablePlaceholder(String translatable) {
        return "%" + MINECRAFT_TRANSLATABLE + translatable + "%";
    }

}
