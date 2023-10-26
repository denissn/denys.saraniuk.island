package ua.javarush.island.entities.organisms.animals;

import ua.javarush.island.entities.behavior.Eat;
import ua.javarush.island.entities.behavior.Movable;
import ua.javarush.island.entities.organisms.Organism;

public abstract class Animal extends Organism implements Movable, Eat {
}
