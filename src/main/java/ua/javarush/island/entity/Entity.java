package ua.javarush.island.entity;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
public abstract class Entity {
    private String name;
    private String icon;
    private int amountMax;

    public Entity reproduce() {
        try {
            return copy(this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T copy(T object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> objectClass = object.getClass();
        T newObject = (T) objectClass.getConstructor().newInstance();
        return newObject;
    }
}
