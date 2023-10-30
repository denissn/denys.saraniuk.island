package ua.javarush.island.utility;

import org.reflections.Reflections;
import ua.javarush.island.entities.abstractions.annotations.Config;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.map.Area;

public class AppConfigurator {

    private final EntityLivingFactory ENTITY_LIVING_FACTORY = EntityLivingFactory.getInstance();
    private final ConfigLoader CONFIG_LOADER = ConfigLoader.getInstance();
    private final AreaInitializator AREA_INITIALIZER = AreaInitializator.getInstance();

    private static class LazyHolder {
        static final AppConfigurator INSTANCE = new AppConfigurator();
    }

    public static AppConfigurator getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Area init() {
        loadEntities();
        return initializeArea();
    }

    private void loadEntities() {
        Reflections reflections = new Reflections("ua.javarush.island.entities.entitiesLiving");
        reflections.getSubTypesOf(EntityLiving.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .map(CONFIG_LOADER::getObject)
                .forEach(ENTITY_LIVING_FACTORY::registerEntity);
    }

    private Area initializeArea() {
        return AREA_INITIALIZER.initializeArea(CONFIG_LOADER, ENTITY_LIVING_FACTORY);
    }
}
