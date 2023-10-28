package ua.javarush.island;

import ua.javarush.island.entities.entitiesLiving.animals.predators.Wolf;
import ua.javarush.island.services.AppConfigurator;

public class IslandApplication {
    public static void main(String[] args) {
        AppConfigurator.getInstance().init();

    }
}
