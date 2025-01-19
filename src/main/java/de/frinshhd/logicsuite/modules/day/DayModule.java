package de.frinshhd.logicsuite.modules.day;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.modules.BaseModule;

public class DayModule extends BaseModule {
    public DayModule() {
        super("day");
    }

    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().day.enabled;
    }
}
