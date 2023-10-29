package ua.javarush.island;


import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Caterpillar;
import ua.javarush.island.entities.entitiesLiving.animals.herbivorous.Duck;
import ua.javarush.island.entities.entitiesLiving.animals.predators.Wolf;
import ua.javarush.island.entities.entitiesLiving.plants.Grass;
import ua.javarush.island.utility.AppConfigurator;
import ua.javarush.island.utility.EntityLivingFactory;

public class IslandApplication {
    public static void main(String[] args) {
        AppConfigurator.getInstance().init();
        EntityLivingFactory entityLivingFactory = EntityLivingFactory.getInstance();
        System.out.println(entityLivingFactory.create(Wolf.class));
        System.out.println(entityLivingFactory.create(Duck.class));
        System.out.println(entityLivingFactory.create(Grass.class));
        System.out.println(entityLivingFactory.create(Caterpillar.class));
    }
}
