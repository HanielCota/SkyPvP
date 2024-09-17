package com.github.hanielcota.logging;

import com.github.hanielcota.utils.ColorUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * This class is used for logging messages in Minecraft with different log levels.
 * It uses the Bukkit API to send messages to the console.
 */
public class MinecraftLogger {

    // The prefix that will be displayed before each log message
    private final String prefix;

    /**
     * Constructor for the MinecraftLogger class.
     *
     * @param prefix The prefix that will be displayed before each log message.
     */
    public MinecraftLogger(String prefix) {
        // Ensure that the prefix itself translates any color codes
        this.prefix = ColorUtils.translateColorCodes(ChatColor.GOLD + "[" + prefix + "] " + ChatColor.RESET);
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to be logged.
     */
    public void info(String message) {
        logMessage(message, LogLevel.INFO);
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to be logged.
     */
    public void warn(String message) {
        logMessage(message, LogLevel.WARNING);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to be logged.
     */
    public void error(String message) {
        logMessage(message, LogLevel.ERROR);
    }

    /**
     * Logs a debug message.
     *
     * @param message The message to be logged.
     */
    public void debug(String message) {
        logMessage(message, LogLevel.DEBUG);
    }

    /**
     * Logs a message with the specified log level.
     *
     * @param message The message to be logged.
     * @param level   The log level of the message.
     */
    private void logMessage(String message, LogLevel level) {
        // Translate color codes in the message
        String coloredMessage = ColorUtils.translateColorCodes(message);
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + level.getColor() + coloredMessage);
    }

    /**
     * Enum representing the different log levels.
     */
    @Getter
    public enum LogLevel {
        INFO(ChatColor.WHITE),
        WARNING(ChatColor.YELLOW),
        ERROR(ChatColor.RED),
        DEBUG(ChatColor.AQUA);

        // The color associated with each log level
        private final ChatColor color;

        /**
         * Constructor for the LogLevel enum.
         *
         * @param color The color associated with the log level.
         */
        LogLevel(ChatColor color) {
            this.color = color;
        }
    }
}
