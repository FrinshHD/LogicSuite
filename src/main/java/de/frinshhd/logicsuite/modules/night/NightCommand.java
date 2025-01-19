package de.frinshhd.logicsuite.modules.night;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.SpigotCommandExecutor;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NightCommand extends SpigotCommandExecutor {
    public NightCommand() {
        super("night");
        setDescription("Set the time to night");
        setUsage("/night");
        setPermission("logicsuite.night");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
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

    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().night.enabled;
    }
}
