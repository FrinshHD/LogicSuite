package de.frinshhd.logicsuite.modules.main;

import de.frinshhd.logicsuite.modules.BaseModule;

public class MainModule extends BaseModule {

    public MainModule() {
        super("main");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
