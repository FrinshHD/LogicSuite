package de.frinshhd.logicsuite.modules.night;

import de.frinshhd.logicsuite.modules.BaseModule;

public class NightModule extends BaseModule {
    public NightModule() {
        super("night");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
