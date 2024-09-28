package de.frinshhd.logicsuite.modules.chat;

import de.frinshhd.logicsuite.modules.BaseModule;
import de.frinshhd.logicsuite.utils.Translator;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatModule extends BaseModule implements Listener {

    private boolean chatLocked = false;

    public ChatModule() {
        super("chat");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("logicsuite.chat.lock.bypass") && isChatLocked()) {
            event.setCancelled(true);
            player.sendMessage(Translator.build("chat.locked"));
        }
    }

    public boolean isChatLocked() {
        return chatLocked;
    }

    public void setChatLocked(boolean chatLocked) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("logicsuite.chat.lock.bypass")) {
                player.sendMessage(Translator.build(chatLocked ? "chat.locked.bypass" : "chat.unlocked.bypass"));
                return;
            }

            player.sendMessage(Translator.build(chatLocked ? "chat.locked" : "chat.unlocked"));
        });

        this.chatLocked = chatLocked;
    }

    public void clearChat() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("logicsuite.chat.clear.bypass")) {
                player.sendMessage(Translator.build("chat.cleared.bypass"));
                return;
            }

            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }

            player.sendMessage(Translator.build("chat.cleared"));
        });
    }
}
