package simulation.actions;

import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.gamemap.GameMapUtils;
import simulation.gamemap.GameMap;

import java.util.List;

public class MoveAction extends Actions {
    private final GameMap gameMap;

    public MoveAction(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void execute() {
        List<Entity> creatures = GameMapUtils.getEntities(gameMap, Creature.class);
        for (Entity entity : creatures) {
            Creature creature = (Creature) entity;
            if (canMove(creature)) {
                creature.makeMove(gameMap);
            }
        }
    }

    private boolean canMove(Creature creature) {
        return GameMapUtils.isOnMap(gameMap, creature);
    }
}
