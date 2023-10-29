package ua.javarush.island;


import ua.javarush.island.entities.entitiesLiving.EntityLiving;
import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Caterpillar;
import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Duck;
import ua.javarush.island.entities.entitiesLiving.animals.predators.Wolf;
import ua.javarush.island.entities.entitiesLiving.plants.Grass;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.ConsoleProvider;
import ua.javarush.island.utility.EntityLivingFactory;

public class IslandApplication {
    public static void main(String[] args) {
        ConsoleProvider consoleProvider = ConsoleProvider.getInstance();
        AppConfigurator.getInstance().init();
        //TODO the code below is used for testing
        EntityLivingFactory entityLivingFactory = EntityLivingFactory.getInstance();
        consoleProvider.println(entityLivingFactory.create(Wolf.class));
        consoleProvider.println(entityLivingFactory.create(Duck.class));
        consoleProvider.println(entityLivingFactory.create(Grass.class));
        consoleProvider.println(entityLivingFactory.create(Caterpillar.class));
    }
}
