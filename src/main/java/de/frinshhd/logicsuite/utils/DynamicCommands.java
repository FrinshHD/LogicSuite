package de.frinshhd.logicsuite.utils;

import de.frinshhd.logicsuite.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicCommands {

    private static Set<SpigotCommandExecutor> commands = new HashSet<>();

    public static void reload() {
        unloadCommands();

        String fullCanonicalName = Main.getInstance().getClass().getCanonicalName();
        String canonicalName = fullCanonicalName.substring(0, fullCanonicalName.lastIndexOf("."));

        Reflections reflections = new Reflections(canonicalName, new SubTypesScanner(false));
        Set<String> classNames = reflections.getAll(new SubTypesScanner(false));

        load(classNames, canonicalName);
    }

    public static void load(Set<String> classNames, String canonicalName) {

        Set<SpigotCommandExecutor> newCommands = new HashSet<>();

        for (String className : classNames) {
            if (className.contains(canonicalName)) {
                try {
                    Class<?> cls = Class.forName(className);

                    Class<SpigotCommandExecutor> commandExecutorClass = SpigotCommandExecutor.class;

                    if (commandExecutorClass.isAssignableFrom(cls)) {
                        Main.getInstance().getLogger().info("[DynamicCommands] Loading command in class " + className);

                        Constructor<?> constructor = cls.getConstructors()[0];
                        SpigotCommandExecutor commandExecutor = (SpigotCommandExecutor) constructor.newInstance();

                        if (!commandExecutor.isEnabled()) continue;

                        newCommands.add(commandExecutor);

                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         InvocationTargetException | IllegalArgumentException e) {

                    Main.getInstance().getLogger().warning("[DynamicCommands] Error loading command in class " + className + " " + e);
                }
            }
        }

        try {

            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            CommandMap commandMap = null;
            try {
                commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            CommandMap finalCommandMap = commandMap;

            commands.forEach(command -> {
                command.unregister(finalCommandMap);
            });

            newCommands.forEach(command -> {
                finalCommandMap.register(Main.getInstance().getName(), command);
            });

            commands = newCommands;

        } catch (Exception e) {
            Main.getInstance().getLogger().warning("[DynamicCommands] Error registering command " + e);
        }
    }

    public static void unloadCommands() {
        Plugin plugin = Main.getInstance();

        try {
            // Access the command map
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Access the known commands
            Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);

            // Iterate and remove commands related to the plugin
            knownCommands.entrySet().removeIf(entry -> {
                Command command = entry.getValue();
                if (command instanceof PluginCommand pluginCommand) {
                    if (pluginCommand.getPlugin().equals(plugin)) {
                        // Explicitly unregister the command
                        pluginCommand.unregister(commandMap);
                        return true; // Remove from the map
                    }
                }
                return false; // Keep other commands
            });

            plugin.getLogger().info("Unloaded all commands for plugin: " + plugin.getName());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to unload commands: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

