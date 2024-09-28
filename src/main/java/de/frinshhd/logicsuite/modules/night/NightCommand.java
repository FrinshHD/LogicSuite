package de.frinshhd.logicsuite.modules.night;

import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand extends SpigotCommandExecutor {
    public NightCommand() {
        super("night");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("logicsuite.night")) {
            sender.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (!(sender  instanceof Player player)) {
            sender.sendMessage(Translator.build("playerOnly"));
            return true;
        }

        player.getWorld().setTime(18000L);
        player.sendMessage(Translator.build("nightSet"));
        return true;
    }
}
