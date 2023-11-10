package ua.javarush.island.entity.animal;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Area;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.EntityFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Animal extends Entity implements Movable, Eating {
    private int maxChanceToEat = 100;
    private double minPercentsWeightToDie = 0.52;
    private double weight;
    private double weightDefault;
    private double weightSaturation;
    private int speedMax;
    private Map<String, Integer> likelyFood;

    @Override
    public void eat(Location location) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        List<String> foodTypes = likelyFood.entrySet().stream().filter(m -> m.getValue() > 0).map(Map.Entry::getKey).collect(Collectors.toList());
        ListIterator<String> iterator = foodTypes.listIterator();
        while (iterator.hasNext()) {
            if (EntityFactory.getEntityClass(iterator.next()) == null) {
                iterator.remove();
            }
        }
        if (!foodTypes.isEmpty()) {
            Collections.shuffle(foodTypes);//get random food
            String food = foodTypes.get(current.nextInt(foodTypes.size()));
            Integer chanceToEat = likelyFood.get(food);
            if (chanceToEat >= current.nextInt(maxChanceToEat)) {
                Class<? extends Entity> targetFood = EntityFactory.getEntityClass(food);
                List<Entity> entityList = location.getEntities().get(targetFood);
                if (entityList != null && !entityList.isEmpty()) {
                    double saturation = 0;
                    if(entityList.get(0) instanceof Animal){
                        saturation = ((Animal) entityList.get(0)).getWeightDefault();
                    } else {
                        saturation = weightSaturation;
                    }
                    weight = weight + saturation;
                    entityList.remove(0);
                }
            }
        }
        weight = weight - weightSaturation;
        if (weight / ((Animal) EntityFactory.getEntityClass(this.getClass())).getWeightDefault() < minPercentsWeightToDie) {
            location.removeEntity(this);
        }
    }

    @Override
    public void move(Area area, Location location) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        Location[][] locations = area.getLocations();
        Area.Direction[] directions = Area.Direction.values();

        int x = location.getX();
        int y = location.getY();
        for (int i = 0; i < current.nextInt(speedMax); i++) {
            int xNew = x + directions[current.nextInt(directions.length)].getX();
            int yNew = y + directions[current.nextInt(directions.length)].getY();
            if (xNew >= 0 && yNew >= 0 && xNew < locations.length && yNew < locations[0].length ){
                List<Entity> entitiesNewLocation = locations[xNew][yNew].getEntities().get(this.getClass());
                if (entitiesNewLocation == null || (entitiesNewLocation.size() + 1 <= getAmountMax())) { // simulation is more fun without this restriction
                    x = xNew;
                    y = yNew;
                }
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
                ", weight=" + getWeight() +
                ", weightDefault=" + getWeightDefault() +
                ", weightSaturation=" + getWeightSaturation() +
                ", speedMax=" + getSpeedMax() +
                ", likelyFood=" + getLikelyFood() +
                '}';
    }
}
