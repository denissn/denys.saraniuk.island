package ua.javarush.island.map;

import lombok.Getter;
import ua.javarush.island.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Location {
    private int x;
    private int y;

    @Getter
    private Map<Class<? extends Entity>, List<Entity>> entities = new ConcurrentHashMap<>();

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addEntity(Entity entity) {
        Class<? extends Entity> aClass = entity.getClass();
        if (!entities.containsKey(aClass)) {
            ArrayList<Entity> entitiesList = new ArrayList<>();
            entitiesList.add(entity);
            entities.put(aClass, entitiesList);
        } else {
            List<Entity> entitiesList = entities.get(aClass);
            entitiesList.add(entity);
        }
    }

    public void removeEntity(Entity entity) {
        Class<? extends Entity> aClass = entity.getClass();
        if (entities.containsKey(aClass)) {
            List<Entity> entitiesList = entities.get(aClass);
            entitiesList.remove(entity);
        }
    }

}
