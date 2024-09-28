package de.frinshhd.logicsuite.config.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TabListModel extends BaseModuleModel {

    @JsonProperty
    private ArrayList<String> header = new ArrayList<>();

    @JsonProperty
    private ArrayList<String> footer = new ArrayList<>();

    @JsonIgnore
    public ArrayList<String> getHeader() {
        return header;
    }

    @JsonIgnore
    public ArrayList<String> getFooter() {
        return footer;
    }
}
