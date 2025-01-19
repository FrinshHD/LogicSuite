package de.frinshhd.logicsuite.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigModel {

    @JsonProperty
    public ScoreboardModel scoreboard = new ScoreboardModel();

    @JsonProperty
    public TabListModel tabList = new TabListModel();

    @JsonProperty
    public BaseModuleModel chat = new BaseModuleModel();

    @JsonProperty
    public BaseModuleModel day = new BaseModuleModel();

    @JsonProperty
    public BaseModuleModel night = new BaseModuleModel();

    @JsonProperty
    public BaseModuleModel weather = new BaseModuleModel();

    @JsonProperty
    public BaseModuleModel teleport = new BaseModuleModel();

    @JsonProperty
    public BaseModuleModel gamemode = new BaseModuleModel();
}
