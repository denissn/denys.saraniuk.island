package ua.javarush.island.abstraction.behavior;

import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Location;

public interface Reproductive {
    public <T extends Entity> T reproduce(Location location);
}
