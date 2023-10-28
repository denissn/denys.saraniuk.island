package ua.javarush.island.entities.entitiesLiving.animals;

import ua.javarush.island.entities.abstractions.interfaces.Eating;
import ua.javarush.island.entities.abstractions.interfaces.Movable;
import ua.javarush.island.entities.entitiesLiving.EntityLiving;

public abstract class Animal extends EntityLiving implements Movable, Eating {



    @Override
    public void eat() {

    }

    @Override
    public void move() {

    }
}
