package ua.javarush.island.entity.animal;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.entity.Entity;
import ua.javarush.island.abstraction.behavior.Eating;
import ua.javarush.island.abstraction.behavior.Movable;

import java.util.Map;

@Getter
@Setter
public abstract class Animal extends Entity implements Movable, Eating {

    private double weightDefault;
    private double weightSaturation;
    private int speedMax;
    private Map<String, Integer> likelyFood;

    @Override
    public void eat() {
        System.out.println("some eat");
    }

    @Override
    public void move() {
        System.out.println("somewhere move");
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
