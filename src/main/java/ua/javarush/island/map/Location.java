package ua.javarush.island.map;

import lombok.Getter;
import ua.javarush.island.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Location {
    private final ReentrantLock lock = new ReentrantLock();
    private final int x;
    private final int y;
    private final Map<Class<? extends Entity>, List<Entity>> entities = new ConcurrentHashMap<>();
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
