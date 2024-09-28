package de.frinshhd.logicsuite.config.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigModel {

    @JsonProperty
    public ScoreboardModel scoreboard = new ScoreboardModel();

    @JsonProperty
    public TabListModel tabList = new TabListModel();
}
