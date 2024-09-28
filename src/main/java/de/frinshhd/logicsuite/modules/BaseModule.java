package de.frinshhd.logicsuite.modules;

public abstract class BaseModule {

    private final String id;

    public BaseModule(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract boolean isEnabled();

}
