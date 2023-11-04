package ua.javarush.island.entity.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.EntityFactory;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
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
            if (!entityList.isEmpty()) {
                //System.out.println(targetFood.getSimpleName());
                entityList.remove(0);
            } else {
                //System.out.println("--------------");
            }
        } else {
            //System.out.println("--------------");
        }

    }

    @Override
    public void move(Location location) {
        System.out.println(this.getName() + " run into the sunset...");
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
