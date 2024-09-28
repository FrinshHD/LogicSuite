package de.frinshhd.logicsuite.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.frinshhd.logicsuite.config.model.ConfigModel;
import de.frinshhd.logicsuite.modules.ScoreboardModule;
import de.frinshhd.logicsuite.utils.DynamicModules;

import java.io.FileInputStream;
import java.io.IOException;

public class ConfigManager {

    private ConfigModel config;

    public ConfigManager() {
        loadConfig();
    }

    public void loadConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            config = mapper.readValue(new FileInputStream("plugins/LogicSuite/config.yml"), ConfigModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConfigModel getConfig() {
        return config;
    }

    public void reloadConfig() {
        loadConfig();
    }
}
