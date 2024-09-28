package de.frinshhd.logicsuite.modules;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.utils.MessageFormat;
import de.frinshhd.logicsuite.utils.PlayerHashMap;
import fr.mrmicky.fastboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class ScoreboardModule extends BaseModule {

    private final PlayerHashMap<UUID, FastBoard> playerBoards = new PlayerHashMap<>();

    private BukkitTask runnable = null;

    public ScoreboardModule() {
        super("scoreboard");
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
                    updateBoard(player);
                });
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1 * 20L);
    }

    public void initBoard(Player player) {
        if (!isEnabled()) {
            return;
        }

        FastBoard board = new FastBoard(player);

        board.updateTitle(MessageFormat.build(Main.getConfigManager().getConfig().scoreboard.getTitle()));

        playerBoards.put(player.getUniqueId(), board);

        runnable();

        updateBoard(player);
    }

    public void updateBoard(Player player) {
        FastBoard board = playerBoards.get(player.getUniqueId());

        if (board == null) {
            return;
        }

        ArrayList<String> lines = new ArrayList<>(Main.getConfigManager().getConfig().scoreboard.getLines());

        lines.replaceAll(line -> PlaceholderAPI.setPlaceholders(player, line));
        lines.replaceAll(MessageFormat::build);

        board.updateLines(lines);
    }


    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().scoreboard.enabled;
    }
}
