package de.frinshhd.logicsuite.utils;

import de.frinshhd.logicsuite.Main;
import de.frinshhd.logicsuite.modules.BaseModule;
import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DynamicModules {

    public static HashMap<String, BaseModule> modules = new HashMap<>();

    public static void load(Set<String> classNames, String canonicalName) {
        Iterator<String> classNamesIterator = classNames.iterator();
        while (classNamesIterator.hasNext()) {
            String className = classNamesIterator.next();

            if (className.contains(canonicalName)) {
                try {
                    Class<?> cls = Class.forName(className);

                    Class<BaseModule> moduleClass = BaseModule.class;

                    if (moduleClass.isAssignableFrom(cls)) {
                        //Main.getInstance().getLogger().info("[DynamicListeners] Loading listener in class " + className);

                        Constructor<?> constructor = cls.getConstructors()[0];
                        BaseModule baseModule = (BaseModule) constructor.newInstance();

                        modules.put(baseModule.getId(), baseModule);

                        //Main.getInstance().getLogger().info("[DynamicListeners] Finished loading listener in  class " + className);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         InvocationTargetException | IllegalArgumentException e) {

                    Main.getInstance().getLogger().warning("[DynamicModules] Error loading listener in class " + className + " " + e);
                }
            }
        }
    }

    public static BaseModule getModule(String id) {
        return modules.get(id);
    }
}

