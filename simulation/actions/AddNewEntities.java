package simulation.actions;

import simulation.entity.Entity;
import simulation.gamemap.GameMapUtils;
import simulation.gamemap.GameMap;

public class AddNewEntities extends Actions {

    private final GameMap gameMap;
    private final int THRESHOLD;
    private final SpawnEntity spawnEntity;
    private final Class<? extends Entity> clazz;
    private final int amount;

    public AddNewEntities(GameMap gameMap, int THRESHOLD, int amount, SpawnEntity spawnEntity, Class<? extends Entity> clazz) {
        this.gameMap = gameMap;
        this.THRESHOLD = THRESHOLD;
        this.spawnEntity = spawnEntity;
        this.clazz = clazz;
        this.amount = amount;
    }

    private void addNewEntity() {
        int count = GameMapUtils.getCountEntity(gameMap, clazz);
        if (count <= THRESHOLD) {
            for (int i = 0; i < amount; i++) {
                spawnEntity.execute();
            }
        }
    }

    @Override
    public void execute() {
        addNewEntity();
    }
}
