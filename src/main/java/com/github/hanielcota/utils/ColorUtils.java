package com.github.hanielcota.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;

/**
 * Utility class for handling color codes in strings for Minecraft.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorUtils {

    /**
     * Translates color codes prefixed with '&' in the given string to
     * Minecraft color codes using ChatColor.
     *
     * @param message The string containing the color codes.
     * @return The formatted string with translated color codes.
     */
    public static String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Strips all color codes from the given string.
     *
     * @param input The string to strip color codes from.
     * @return The string without color codes.
     */
    public static String stripColor(String input) {
        return ChatColor.stripColor(input);
    }
}
