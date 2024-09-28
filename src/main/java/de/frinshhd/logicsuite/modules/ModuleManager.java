package de.frinshhd.logicsuite.modules;

import de.frinshhd.logicsuite.config.model.TabListModel;
import de.frinshhd.logicsuite.utils.DynamicModules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ModuleManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ScoreboardModule scoreboardModule = (ScoreboardModule) DynamicModules.getModule("scoreboard");

        if (scoreboardModule != null) {
            scoreboardModule.initBoard(player);
        }

        TabListModule tabListModel = (TabListModule) DynamicModules.getModule("tablist");

        if (tabListModel != null) {
            tabListModel.initTabList(player);
        }
    }



}
