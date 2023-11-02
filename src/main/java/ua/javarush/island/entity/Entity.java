package ua.javarush.island.entity;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.behavior.Reproductive;
import ua.javarush.island.entity.animal.Animal;
import ua.javarush.island.entity.plant.Plant;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.EntityFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@Setter
public abstract class Entity implements Reproductive {
    private String name;
    private String icon;
    private int amountMax;
    private boolean isReproduced = false;

    @Override
    public <T extends Entity> T reproduce(Location location) {
        if (this.isReproduced) {
            return null;
        }
        List<Entity> entityList = location.getEntities().get(this.getClass());
        if (entityList.size() < this.getAmountMax()) {
            this.setReproduced(true);
            Optional<Entity> nextParent = entityList.stream().filter(Predicate.not(Entity::isReproduced)).findFirst();
            if (nextParent.isPresent()) {
                T newEntity = (T) EntityFactory.getEntity(this.getClass());
                nextParent.get().setReproduced(true);
                newEntity.setReproduced(true);
                return newEntity;
            }
        }
        return null;
    }

    public boolean isReproduced(){
        return isReproduced;
    }
}
