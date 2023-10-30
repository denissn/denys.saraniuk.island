package ua.javarush.island.utility;

import org.reflections.Reflections;
import ua.javarush.island.entities.abstractions.annotations.Config;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AppConfigurator {

    private final EntityLivingFactory ENTITY_LIVING_FACTORY = EntityLivingFactory.getInstance();
    private final ConfigLoader CONFIG_LOADER = ConfigLoader.getInstance();

    private static class LazyHolder {
        static final AppConfigurator INSTANCE = new AppConfigurator();
    }

    public static AppConfigurator getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Area init() {
        registerEntities();
        return initializeArea();
    }

    private void registerEntities() {
        Reflections reflections = new Reflections("ua.javarush.island.entities.entitiesLiving");
        reflections.getSubTypesOf(EntityLiving.class).stream()
                .filter(e -> e.isAnnotationPresent(Config.class))
                .map(CONFIG_LOADER::getObject)
                .forEach(ENTITY_LIVING_FACTORY::registerEntity);
    }

    private Area initializeArea() {

        //TODO move to another class
        Area area = CONFIG_LOADER.getObject(Area.class); //game field loading
        Location[][] locations = area.getLocations();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Map<Class<? extends EntityLiving>, EntityLiving> entitiesLiving = ENTITY_LIVING_FACTORY.getEntitiesLiving();
                locations[i][j] = new Location(i, j);
                for (Map.Entry<Class<? extends EntityLiving>, EntityLiving> entityItem : entitiesLiving.entrySet()) {
                    int amountMax = entityItem.getValue().amountMax;
                    int entitiesByType = random.nextInt(amountMax);
                    for (int k = 0; k < entitiesByType; k++) {
                        locations[i][j].addEntity(entityItem.getValue().reproduce());
                    }
                }
            }
        }
        return area;
    }
}
