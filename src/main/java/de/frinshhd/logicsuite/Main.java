package de.frinshhd.logicsuite;

import de.frinshhd.logicsuite.config.ConfigManager;
import de.frinshhd.logicsuite.utils.DynamicCommands;
import de.frinshhd.logicsuite.utils.DynamicListeners;
import de.frinshhd.logicsuite.utils.DynamicModules;
import de.frinshhd.logicsuite.utils.Translator;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static JavaPlugin instance;
    private static ConfigManager configManager;

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }


    @Override
    public void onEnable() {
        instance = this;
        Main.getInstance().getLogger().setLevel(Level.ALL);

        //create files
        new File("plugins/LogicSuite").mkdir();

        List<String> files = new ArrayList<>();
        files.addAll(List.of("config.yml", "messages.properties"));

        for (String fileRaw : files) {
            File file = new File("plugins/LogicSuite/" + fileRaw);
            if (file.exists()) {
                continue;
            }

            InputStream link = (Main.class.getClassLoader().getResourceAsStream(fileRaw));
            try {
                Files.copy(link, file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        configManager = new ConfigManager();

        String fullCanonicalName = Main.getInstance().getClass().getCanonicalName();
        String canonicalName = fullCanonicalName.substring(0, fullCanonicalName.lastIndexOf("."));

        Reflections reflections = new Reflections(canonicalName, new SubTypesScanner(false));
        Set<String> classNames = reflections.getAll(new SubTypesScanner(false));

        DynamicModules.load(classNames, canonicalName);
        DynamicCommands.load(classNames, canonicalName);
        DynamicListeners.load(classNames, canonicalName);

        // register messages
        try {
            Translator.register("plugins/LogicSuite/messages.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
