package ua.javarush.island.map;

import ua.javarush.island.entities.Entity;

import java.util.Map;

public class Location {
    private int x;
    private int y;

    private Map<Class<? extends Entity>, Entity> entities;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map<Class<? extends Entity>, Entity> getEntities() {
        return entities;
    }

    public void setEntities(Map<Class<? extends Entity>, Entity> entities) {
        this.entities = entities;
    }


}
