package ua.javarush.island.utility;

import org.reflections.Reflections;
import ua.javarush.island.entities.abstractions.annotations.Config;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.map.Area;

import java.util.Set;
import java.util.stream.Collectors;

public class AppConfigurator {

    private static final EntityLivingFactory ENTITY_LIVING_FACTORY = EntityLivingFactory.getInstance();
    private static final ConfigLoader CONFIG_LOADER = ConfigLoader.getInstance();

    private static class LazyHolder {
        static final AppConfigurator INSTANCE = new AppConfigurator();
    }

    public static AppConfigurator getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void init() {
        registerEntities();
        initializeArea();

    }

    private void registerEntities() {
        Reflections reflections = new Reflections("ua.javarush.island.entities.entitiesLiving");
        reflections.getSubTypesOf(EntityLiving.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .map(CONFIG_LOADER::getObject)
                .forEach(ENTITY_LIVING_FACTORY::registerEntity);
    }

    private void initializeArea() {
        Area area = new Area(100, 20);

    }
}
