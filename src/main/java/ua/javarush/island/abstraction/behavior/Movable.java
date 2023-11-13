package ua.javarush.island.abstraction.behavior;

import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

public interface Movable {
    public void move(Area area, Location location);
}
