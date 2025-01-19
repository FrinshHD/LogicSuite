package de.frinshhd.logicsuite.modules;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.MessageFormat;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TabListModule extends BaseModule {

    private BukkitTask runnable = null;

    public TabListModule() {
        super("tablist");
    }

    public void runnable() {
        if (!isEnabled()) {
            return;
        }

        if (runnable != null && !runnable.isCancelled()) {
            return;
        }

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    runnable = null;
                    cancel();
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(player -> {
                    updateTablist(player);
                });
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1 * 20L);
    }

    public void initTabList(Player player) {
        if (!isEnabled()) {
            return;
        }

        updateTablist(player);

        runnable();
    }

    public void updateTablist(Player player) {
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder footerBuilder = new StringBuilder();

        Main.getConfigManager().getConfig().tabList.getHeader().forEach(string -> {
            headerBuilder.append(string).append("\n");
        });

        Main.getConfigManager().getConfig().tabList.getFooter().forEach(string -> {
            footerBuilder.append(string).append("\n");
        });

        Component header = MessageFormat.buildComponent(PlaceholderAPI.setPlaceholders(player, headerBuilder.toString()));

        Component footer = MessageFormat.buildComponent(PlaceholderAPI.setPlaceholders(player, footerBuilder.toString()));

        player.sendPlayerListHeaderAndFooter(header, footer);
    }


    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().scoreboard.enabled;
    }
}
