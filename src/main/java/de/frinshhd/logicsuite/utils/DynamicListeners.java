package de.frinshhd.logicsuite.utils;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.modules.BaseModule;
import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;

public class DynamicListeners {

    public static void load(Set<String> classNames, String canonicalName) {
        Iterator<String> classNamesIterator = classNames.iterator();
        while (classNamesIterator.hasNext()) {
            String className = classNamesIterator.next();

            if (className.contains(canonicalName)) {
                try {
                    Class<?> cls = Class.forName(className);

                    if (BaseModule.class.isAssignableFrom(cls)) {
                        continue;
                    }

                    if (Listener.class.isAssignableFrom(cls)) {
                        Constructor<?> constructor = cls.getConstructors()[0];
                        Listener listener = (Listener) constructor.newInstance();
                        Main.getInstance().getServer().getPluginManager().registerEvents(listener, Main.getInstance());
                        Main.getInstance().getLogger().info("[DynamicListeners] Finished loading listener in class " + className);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         InvocationTargetException | IllegalArgumentException e) {
                    Main.getInstance().getLogger().warning("[DynamicListeners] Error loading listener in class " + className + " " + e);
                }
            }
        }
    }
}