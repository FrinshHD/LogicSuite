package de.frinshhd.logicsuite.config.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ScoreboardModel extends BaseModuleModel {

    @JsonProperty
    private String title = null;

    @JsonProperty
    private ArrayList<String> lines = new ArrayList<>();

    @JsonIgnore
    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public ArrayList<String> getLines() {
        return lines;
    }
}
