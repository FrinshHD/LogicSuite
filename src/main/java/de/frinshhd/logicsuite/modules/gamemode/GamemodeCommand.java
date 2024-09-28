package de.frinshhd.logicsuite.modules.gamemode;

import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import de.frinshhd.logicsuite.utils.TranslatorPlaceholder;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand extends SpigotCommandExecutor {
    public GamemodeCommand() {
        super("gamemode");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender  instanceof Player player)) {
            sender.sendMessage(Translator.build("playerOnly"));
            return true;
        }

        if (!player.hasPermission("logicsuite.gamemode")) {
            player.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Translator.build("gamemode.current", new TranslatorPlaceholder("gamemode", player.getGameMode().name().toLowerCase())));
            return true;
        }

        switch (args[0]) {
            case "0", "survival" -> {
                if (!player.hasPermission("logicsuite.gamemode.survival")) {
                    player.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Translator.build("gamemode.success", new TranslatorPlaceholder("gamemode", "survival")));
                return true;
            }
            case "1", "creative" -> {
                if (!player.hasPermission("logicsuite.gamemode.creative")) {
                    player.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Translator.build("gamemode.success", new TranslatorPlaceholder("gamemode", "creative")));
                return true;
            }
            case "2", "adventure" -> {
                if (!player.hasPermission("logicsuite.gamemode.adventure")) {
                    player.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Translator.build("gamemode.success", new TranslatorPlaceholder("gamemode", "adventure")));
                return true;
            }
            case "3", "spectator" -> {
                if (!player.hasPermission("logicsuite.gamemode.spectator")) {
                    player.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Translator.build("gamemode.success", new TranslatorPlaceholder("gamemode", "spectator")));
                return true;
            }
            default -> {
                player.sendMessage(Translator.build("gamemode.invalid"));
                return true;
            }
        }

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> possibleCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (!sender.hasPermission("logicsuite.gamemode")) {
                return new ArrayList<>();
            }

            possibleCompletions.addAll(List.of("0", "1", "2", "3", "survival", "creative", "adventure", "spectator"));

            for (String possibleCompletion : possibleCompletions) {
                if (possibleCompletion.startsWith(args[0].toLowerCase())) {
                    completions.add(possibleCompletion);
                }
            }
        }

        return completions;
    }
}
