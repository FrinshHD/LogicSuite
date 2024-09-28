package de.frinshhd.logicsuite.modules.day;

import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand extends SpigotCommandExecutor {
    public DayCommand() {
        super("day");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("logicsuite.day")) {
            sender.sendMessage(Translator.build("noPermission"));
            return true;
        }

        if (!(sender  instanceof Player player)) {
            sender.sendMessage(Translator.build("playerOnly"));
            return true;
        }

        player.getWorld().setTime(6000L);
        player.sendMessage(Translator.build("daySet"));
        return true;
    }
}
