package de.frinshhd.logicsuite.modules.gamemode;

import de.frinshhd.logicsuite.modules.BaseModule;

public class GamemodeModule extends BaseModule {
    public GamemodeModule() {
        super("gamemode");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
