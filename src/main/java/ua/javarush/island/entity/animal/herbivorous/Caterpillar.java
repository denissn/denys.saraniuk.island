package ua.javarush.island.entity.animal.herbivorous;

import ua.javarush.island.abstraction.annotation.Config;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;

@Config(filePath = "src/main/resources/config/entity/animal/herbivorous/caterpillar.json")
public class Caterpillar extends Herbivor {
    @Override
    public void move(Area area, Location location) {
        //System.out.println("do nothing");
    }
}
