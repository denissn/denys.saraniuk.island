package ua.javarush.island.entity.animal;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.EntityFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Animal extends Entity implements Movable, Eating {
    private int maxChanceToEat = 100;

    private double weightDefault;
    private double weightSaturation;
    private int speedMax;
    private Map<String, Integer> likelyFood;

    @Override
    public void eat(Location location) {
        //System.out.print(this.getName() + " eat a ");
        ThreadLocalRandom current = ThreadLocalRandom.current();
        List<String> foodTypes = likelyFood.entrySet().stream().filter(m -> m.getValue() > 0).map(Map.Entry::getKey).collect(Collectors.toList());
        Collections.shuffle(foodTypes);//get random food
        String food = foodTypes.get(current.nextInt(foodTypes.size()));
        Integer chanceToEat = likelyFood.get(food);
        if (chanceToEat >= current.nextInt(maxChanceToEat)) {
            Class<? extends Entity> targetFood = EntityFactory.getEntityClass(food);
            List<Entity> entityList = location.getEntities().get(targetFood);
            if (entityList != null && !entityList.isEmpty()) {
                //System.out.println(targetFood.getSimpleName());
                weightDefault = weightDefault + weightSaturation;//TODO
                entityList.remove(0);
            } else {
                //System.out.println("--------------");
            }
        } else {
            //System.out.println("--------------");
        }
        weightDefault = weightDefault - weightSaturation;
        if (weightDefault / ((Animal) EntityFactory.getEntityClass(this.getClass())).getWeightDefault() < 0.6) {
            location.removeEntity(this);
        }
    }

    @Override
    public void move(Area area, Location location) {
//        System.out.println(this.getName() + " run into the sunset...");
        ThreadLocalRandom current = ThreadLocalRandom.current();
        Location[][] locations = area.getLocations();
        Area.Direction[] directions = Area.Direction.values();

        /*int x = location.getX() + directions[current.nextInt(directions.length)].getX();
        int y = location.getY() + directions[current.nextInt(directions.length)].getY();
        if (x >= 0 && y >= 0 && x < locations.length && y < locations[0].length) {
            locations[x][y].addEntity(this);
            location.removeEntity(this);
        }*/

        int x = location.getX();
        int y = location.getY();
        for (int i = 0; i < current.nextInt(speedMax); i++) {
            int xNew = x + directions[current.nextInt(directions.length)].getX();
            int yNew = y + directions[current.nextInt(directions.length)].getY();
            if (xNew >= 0 && yNew >= 0 && xNew < locations.length && yNew < locations[0].length) {
                x = xNew;
                y = yNew;
            }
        }
        locations[x][y].addEntity(this);
        location.removeEntity(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + getName() +
                ", icon=" + getIcon() +
                ", amountMax=" + getAmountMax() +
                ", weightDefault=" + getWeightDefault() +
                ", weightSaturation=" + getWeightSaturation() +
                ", speedMax=" + getSpeedMax() +
                ", likelyFood=" + getLikelyFood() +
                '}';
    }
}
