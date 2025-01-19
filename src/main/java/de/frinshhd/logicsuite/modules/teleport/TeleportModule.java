package de.frinshhd.logicsuite.modules.teleport;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.modules.BaseModule;

public class TeleportModule extends BaseModule {
    public TeleportModule() {
        super("teleport");
    }

    @Override
    public boolean isEnabled() {
        return Main.getConfigManager().getConfig().teleport.enabled;
    }
}
