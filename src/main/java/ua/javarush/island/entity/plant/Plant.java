package ua.javarush.island.entity.plant;

import ua.javarush.island.entity.Entity;

public class Plant extends Entity {

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name=" + getName() +
                ", icon=" + getIcon() +
                ", amountMax=" + getAmountMax() +
                '}';
    }
}
