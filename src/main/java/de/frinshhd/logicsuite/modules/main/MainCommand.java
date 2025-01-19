package de.frinshhd.logicsuite.modules.main;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.DynamicCommands;
import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends SpigotCommandExecutor {
    public MainCommand() {
        super("logicsuite");
        setDescription("Main command for LogicSuite");
        setUsage("/logicsuite <reload>");
        setPermission("logicsuite.main");
        setAliases(List.of("ls"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("logicsuite.main")) {
            sender.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Translator.build("main.usage"));
            return true;
        }

        switch (args[0]) {
            case "reload" -> {
                if (!sender.hasPermission("logicsuite.reload")) {
                    sender.sendMessage(Translator.build("noPermission"));
                    return true;
                }

                Main.getConfigManager().reloadConfig();
                DynamicCommands.reload();
                sender.sendMessage(Translator.build("main.reloaded"));
                return true;
            }
            default -> {
                sender.sendMessage(Translator.build("main.usage"));
                return true;
            }
        }

    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> possibleCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("logicsuite.reload")) {
                possibleCompletions.add("reload");
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
        return true;
    }
}
