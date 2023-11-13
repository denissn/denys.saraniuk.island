package ua.javarush.island.utility;

import org.reflections.Reflections;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.abstraction.annotation.Config;
import ua.javarush.island.map.Area;

public class AppConfigurator {

    private final EntityFactory entityFactory;
    private final ConfigLoader configLoader = new ConfigLoader();
    private final AreaInitializer areaInitializer;
    private final String classFolder = "ua.javarush.island.entity";

    public AppConfigurator(EntityFactory entityFactory, ConsoleProvider consoleProvider) {
        this.entityFactory = entityFactory;
        this.areaInitializer = new AreaInitializer(consoleProvider);
    }

    public Area init() {
        loadEntities();
        return initializeArea();
    }

    private void loadEntities() {
        Reflections reflections = new Reflections(classFolder);
        reflections.getSubTypesOf(Entity.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .map(configLoader::getObject)
                .forEach(entityFactory::registerEntity);
    }

    private Area initializeArea() {
        return areaInitializer.initializeArea();
    }

}
