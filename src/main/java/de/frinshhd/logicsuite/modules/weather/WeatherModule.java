package de.frinshhd.logicsuite.modules.weather;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.modules.BaseModule;
import de.frinshhd.logicsuite.utils.Translator;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherModule extends BaseModule implements Listener {

    private boolean weatherLocked = false;

    public WeatherModule() {
        super("weather");
    }

    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().weather.enabled;
    }

    public void setWeatherLocked(boolean weatherLocked) {
        this.weatherLocked = weatherLocked;
    }

    public boolean isWeatherLocked() {
        return weatherLocked;
    }

    @EventHandler
    public void onWeatherChane(WeatherChangeEvent event) {
        if (isWeatherLocked()) {
            event.setCancelled(true);
        }
    }

    public void setWeather(String weather) {
        switch (weather) {
            case "clear":
                Bukkit.getWorlds().forEach(world -> world.setStorm(false));
                break;
            case "rain":
                Bukkit.getWorlds().forEach(world -> world.setStorm(true));
                break;
            case "thunder":
                Bukkit.getWorlds().forEach(world -> world.setThundering(true));
                break;
        }

    }
}
