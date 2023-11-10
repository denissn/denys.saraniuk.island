package ua.javarush.island.entity;

import lombok.Getter;
import lombok.Setter;
import ua.javarush.island.abstraction.behavior.Reproductive;
import ua.javarush.island.map.Location;
import ua.javarush.island.utility.EntityFactory;

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

    public boolean isReproduced(){
        return isReproduced;
    }
    @Override
    public <T extends Entity> T reproduce(Location location) {
        if (this.isReproduced) {
            return null;
        }
        List<Entity> sameEntities = location.getEntities().get(this.getClass());
        if (sameEntities.size() < this.getAmountMax()) {
            this.setReproduced(true);
            Optional<Entity> nextParent = sameEntities.stream().filter(Predicate.not(Entity::isReproduced)).findFirst();
            if (nextParent.isPresent()) {
                T newEntity = (T) EntityFactory.getEntityClass(this.getClass());
                nextParent.get().setReproduced(true);
                newEntity.setReproduced(true);
                return newEntity;
            }
        }
        return null;
    }

}
