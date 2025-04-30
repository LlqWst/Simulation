package simulation.actions;

import simulation.CreatureActivity;
import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.gamemap.GameMapUtils;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.List;

public class MoveAction extends Actions {
    private final GameMap gameMap;
    private final CreatureActivity creatureActivity;

    public MoveAction(GameMap gameMap, CreatureActivity creatureActivity) {
        this.gameMap = gameMap;
        this.creatureActivity = creatureActivity;
    }

    @Override
    public void execute() {
        List<Entity> creatures = GameMapUtils.getEntities(gameMap, Creature.class);
        for (Entity entity : creatures) {
            Creature creature = (Creature) entity;
            if (canMove(creature)) {
                Coordinates startCoordinates = gameMap.getCoordinates(entity);
                creatureActivity.doActivity(creature, startCoordinates);
            }
        }
    }

    private boolean canMove(Creature creature) {
        return GameMapUtils.isOnMap(gameMap, creature);
    }
}
