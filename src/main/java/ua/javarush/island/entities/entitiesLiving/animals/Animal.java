package ua.javarush.island.entities.entitiesLiving.animals;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.javarush.island.entities.abstractions.interfaces.Eating;
import ua.javarush.island.entities.abstractions.interfaces.Movable;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;

@Getter
@Setter
public abstract class Animal extends EntityLiving implements Movable, Eating {

    public double weightDefault;
    public double weightSaturation;
    public int speedMax;

    @Override
    public void eat() {

    }

    @Override
    public void move() {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", amountMax=" + amountMax +
                ", weightDefault=" + weightDefault +
                ", weightSaturation=" + weightSaturation +
                ", speedMax=" + speedMax +
                '}';
    }
}
