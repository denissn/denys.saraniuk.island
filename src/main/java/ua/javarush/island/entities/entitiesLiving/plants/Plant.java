package ua.javarush.island.entities.entitiesLiving.plants;

import ua.javarush.island.entities.entitiesLiving.EntityLiving;

public class Plant extends EntityLiving {
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", amountMax=" + amountMax +
                '}';
    }
}
