package de.frinshhd.logicsuite.modules.main;

import de.frinshhd.logicsuite.modules.BaseModule;
import de.frinshhd.logicsuite.utils.Translator;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MainModule extends BaseModule {

    public MainModule() {
        super("main");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
