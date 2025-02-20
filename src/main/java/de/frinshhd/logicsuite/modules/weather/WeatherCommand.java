package de.frinshhd.logicsuite.modules.weather;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.DynamicModules;
import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeatherCommand extends SpigotCommandExecutor {
    public WeatherCommand() {
        super("weather");
        setDescription("Change the weather");
        setUsage("/weather <freeze|unfreeze|clear|rain|thunder>");
        setPermission("logicsuite.weather");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("logicsuite.weather")) {
            sender.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Translator.build("weather.usage"));
            return true;
        }

        WeatherModule weatherModule = (WeatherModule) DynamicModules.getModule("weather");

        switch (args[0]) {
            case "freeze" -> {
                if (!sender.hasPermission("logicsuite.weather.freeze")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                weatherModule.setWeatherLocked(true);
                sender.sendMessage(Translator.build("weather.locked"));
                return true;
            }
            case "unfreeze" -> {
                if (!sender.hasPermission("logicsuite.weather.freeze")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                weatherModule.setWeatherLocked(false);
                sender.sendMessage(Translator.build("weather.unlocked"));
                return true;
            }
            case "clear" -> {
                if (!sender.hasPermission("logicsuite.weather.change")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                weatherModule.setWeather("clear");
                sender.sendMessage(Translator.build("weather.clear"));
                return true;
            }
            case "rain" -> {
                if (!sender.hasPermission("logicsuite.weather.change")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                weatherModule.setWeather("rain");
                sender.sendMessage(Translator.build("weather.rain"));
                return true;
            }
            case "thunder" -> {
                if (!sender.hasPermission("logicsuite.weather.change")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                weatherModule.setWeather("thunder");
                sender.sendMessage(Translator.build("weather.thunder"));
                return true;
            }
            default -> {
                sender.sendMessage(Translator.build("weather.usage"));
            }
        }

        return true;

    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> possibleCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (!sender.hasPermission("logicsuite.weather")) {
                return new ArrayList<>();
            }

            if (sender.hasPermission("logicsuite.weather.freeze")) {
                possibleCompletions.addAll(List.of("freeze", "unfreeze"));
            }

            if (sender.hasPermission("logicsuite.weather.change")) {
                possibleCompletions.addAll(List.of("clear", "rain", "thunder"));
            }

            for (String possibleCompletion : possibleCompletions) {
                if (possibleCompletion.startsWith(args[0].toLowerCase())) {
                    completions.add(possibleCompletion);
                }
            }
        }

        return completions;
    }

    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().weather.enabled;
    }
}
