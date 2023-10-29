package ua.javarush.island.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public abstract class Entity {
    public String name;
    public String icon;
    public int amountMax;
}
