package de.frinshhd.logicsuite.modules.chat;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.DynamicModules;
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

public class ChatCommand extends SpigotCommandExecutor {
    public ChatCommand() {
        super("chat");
        setDescription("Toggle the chat lock, unlock or clear the chat.");
        setUsage("/chat <lock|unlock|clear>");
        setPermission("logicsuite.chat");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("logicsuite.chat")) {
            sender.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Translator.build("chat.usage"));
            return true;
        }

        ChatModule chatModule = (ChatModule) DynamicModules.getModule("chat");

        switch (args[0]) {
            case "lock" -> {
                if (!sender.hasPermission("logicsuite.chat.lock")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                chatModule.setChatLocked(true);
                sender.sendMessage(Translator.build("chat.locked"));
                return true;
            }
            case "unlock" -> {
                if (!sender.hasPermission("logicsuite.chat.lock")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                chatModule.setChatLocked(false);
                sender.sendMessage(Translator.build("chat.unlocked"));
                return true;
            }
            case "clear" -> {
                if (!sender.hasPermission("logicsuite.chat.clear")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                chatModule.clearChat();
                sender.sendMessage(Translator.build("chat.cleared"));
                return true;
            }
            default -> {
                sender.sendMessage(Translator.build("chat.usage"));
            }
        }

        return true;

    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> possibleCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (!sender.hasPermission("logicsuite.chat")) {
                return new ArrayList<>();
            }

            if (sender.hasPermission("logicsuite.chat.lock")) {
                possibleCompletions.addAll(List.of("lock", "unlock"));
            }

            if (sender.hasPermission("logicsuite.chat.clear")) {
                possibleCompletions.add("clear");
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
        return Main.getConfigManager().getConfig().chat.enabled;
    }
}
