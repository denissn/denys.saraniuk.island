package ua.javarush.island.utility;

import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class AreaInitializator {
    private AreaInitializator(){

    }

    private static class LazyHolder {
        static final AreaInitializator INSTANCE = new AreaInitializator();
    }

    public static AreaInitializator getInstance() {
        return AreaInitializator.LazyHolder.INSTANCE;
    }

    public Area initializeArea(ConfigLoader configLoader, EntityLivingFactory entityLivingFactory){
        Area area = configLoader.getObject(Area.class); //game field loading
        Location[][] locations = area.getLocations();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                Map<Class<? extends EntityLiving>, EntityLiving> entitiesLiving = entityLivingFactory.getEntitiesLiving();
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
