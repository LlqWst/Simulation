package simulation.actions;

import simulation.CreatureActivity;
import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.HashMap;
import java.util.Map;

public class MakeMove extends Actions {
    private final GameMap gameMap;
    private final CreatureActivity creatureActivity;

    public MakeMove(GameMap gameMap, CreatureActivity creatureActivity) {
        this.gameMap = gameMap;
        this.creatureActivity = creatureActivity;
    }

    @Override
    public void execute() {
        HashMap<Coordinates, Creature> creatures = gameMap.getCreatures();
        for (Map.Entry<Coordinates, Creature> entry : creatures.entrySet()) {
            Coordinates startCoordinates = entry.getKey();
            Creature creature = entry.getValue();
            Class<? extends Entity> goal = creature.getGoal();
            if (canMove(goal, creature, startCoordinates)) {
                creatureActivity.doActivity(creature, startCoordinates);
            }
        }
    }

    private boolean canMove(Class<? extends Entity> goal, Creature creature, Coordinates startCoordinates) {
        return gameMap.isAlive(creature)
                && !gameMap.isEmpty(startCoordinates)
                && gameMap.isContains(goal);
    }
}
