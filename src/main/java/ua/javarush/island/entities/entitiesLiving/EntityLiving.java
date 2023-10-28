package ua.javarush.island.entities.entitiesLiving;

import ua.javarush.island.entities.Entity;
import ua.javarush.island.entities.abstractions.interfaces.Reproductive;

import java.lang.reflect.InvocationTargetException;

public abstract class EntityLiving extends Entity implements Reproductive {

    @Override
    public EntityLiving reproduce() {
        try {
            return this.getClass().getConstructor(int.class).newInstance(100);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
