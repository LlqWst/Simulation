package simulation.actions;

import simulation.entity.Entity;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

import java.util.function.Supplier;

public class SpawnEntity extends Actions{
    private final GameMap gameMap;
    private final Supplier<Entity> supplier;

    public SpawnEntity(GameMap gameMap, Supplier<Entity> supplier) {
        this.gameMap = gameMap;
        this.supplier = supplier;
    }

    private void setCreature(){
        Entity entity = supplier.get();
        Coordinates coordinates = gameMap.getRandomEmptyCoordinates();
        gameMap.setEntities(coordinates, entity);
    }

    @Override
    public void execute(){
        setCreature();
    }
}
