package de.frinshhd.logicsuite.modules.teleport;

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

public class TeleportCommand extends SpigotCommandExecutor {
    public TeleportCommand() {
        super("teleport");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender  instanceof Player player)) {
            sender.sendMessage(Translator.build("playerOnly"));
            return true;
        }

        if (!player.hasPermission("logicsuite.teleport")) {
            player.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Translator.build("teleport.usage"));
            return true;
        }

        if (args.length == 1) {
            Player target = player.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(Translator.build("teleport.notOnline"));
                return true;
            }

            player.teleport(target);
            player.sendMessage(Translator.build("teleport.success", new TranslatorPlaceholder("target", target.getName())));
            return true;
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> possibleCompletions = new ArrayList<>();

        if (args.length == 1) {
            if (!sender.hasPermission("logicsuite.teleport")) {
                return new ArrayList<>();
            }

            for (Player player : sender.getServer().getOnlinePlayers()) {
                possibleCompletions.add(player.getName());
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
