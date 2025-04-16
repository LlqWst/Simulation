package simulation.actions;

import simulation.entity.Entity;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

import java.util.function.Supplier;

public class SpawnCreature extends Actions{
    private final GameMap gameMap;
    private final Supplier<Entity> supplier;

    public SpawnCreature(GameMap gameMap, Supplier<Entity> supplier) {
        this.gameMap = gameMap;
        this.supplier = supplier;
    }

    private void setCreature(){
        Entity entity = supplier.get();
        Coordinates coordinates = gameMap.getRandomEmptyCoordinates();
        gameMap.setEntities(coordinates, entity);
    }

    public void execute(){
        setCreature();
    }
}
