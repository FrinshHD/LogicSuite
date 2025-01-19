package de.frinshhd.logicsuite.utils;

import org.bukkit.command.Command;

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
