package de.frinshhd.logicsuite.modules.main;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.DynamicModules;
import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends SpigotCommandExecutor {
    public MainCommand() {
        super("logicsuite");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
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
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
}
