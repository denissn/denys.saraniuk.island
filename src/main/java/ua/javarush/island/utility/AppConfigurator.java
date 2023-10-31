package ua.javarush.island.utility;

import org.reflections.Reflections;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.abstraction.annotation.Config;
import ua.javarush.island.map.Area;

public class AppConfigurator {

    private final EntityFactory entityFactory;
    private final ConfigLoader configLoader = new ConfigLoader();
    private final ConsoleProvider consoleProvider;
    private final AreaInitializer areaInitializer;

    public AppConfigurator(EntityFactory entityFactory, ConsoleProvider consoleProvider) {
        this.entityFactory = entityFactory;
        this.consoleProvider = consoleProvider;
        this.areaInitializer = new AreaInitializer(consoleProvider);
    }

    public Area init() {
        loadEntities();
        return initializeArea();
    }

    private void loadEntities() {
        Reflections reflections = new Reflections("ua.javarush.island.entity");
        reflections.getSubTypesOf(Entity.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .map(configLoader::getObject)
                .forEach(entityFactory::registerEntity);
    }

    private Area initializeArea() {
        return areaInitializer.initializeArea(entityFactory);
    }

}
