package simulation.actions;

import simulation.entity.Entity;
import simulation.gamemap.GameMapUtils;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.function.Supplier;

public class SpawnEntity extends Actions {
    private final GameMap gameMap;
    private final Supplier<Entity> supplier;

    public SpawnEntity(GameMap gameMap, Supplier<Entity> supplier) {
        this.gameMap = gameMap;
        this.supplier = supplier;
    }

    private void setCreature() {
        Entity entity = supplier.get();
        Coordinates coordinates = GameMapUtils.getRandomEmptyCoordinates(gameMap);
        gameMap.setEntity(coordinates, entity);
    }

    @Override
    public void execute() {
        setCreature();
    }
}
