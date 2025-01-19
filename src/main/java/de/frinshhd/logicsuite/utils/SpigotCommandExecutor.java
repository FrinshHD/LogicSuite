package de.frinshhd.logicsuite.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SpigotCommandExecutor extends Command {
    public SpigotCommandExecutor(String name, String permission) {
        super(name);
        this.setPermission(permission);
    }

    public SpigotCommandExecutor(String commandName) {
        super(commandName);
    }

    public abstract boolean isEnabled();
}
