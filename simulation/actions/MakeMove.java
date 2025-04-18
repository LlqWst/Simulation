package simulation.actions;

import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;

import java.util.List;

public class MakeMove extends Actions{
    private final GameMap gameMap;
    private final CreatureAction creatureAction;


    public MakeMove(GameMap gameMap, CreatureAction creatureAction) {
        this.gameMap = gameMap;
        this.creatureAction = creatureAction;
    }

    @Override
    public void execute () {
        List<Coordinates> creatures = gameMap.getCreatures();
        for (Coordinates startCoordinates : creatures){
            if(isEmpty(startCoordinates)){
                continue;
            }
            Entity creature = gameMap.getEntity(startCoordinates);
            if(creature instanceof Herbivore herbivore){
                herbivoreMove(herbivore, startCoordinates);
            } else if(creature instanceof Predator predator){
                predatorMove(predator, startCoordinates);
            } else throw new IllegalArgumentException();
        }
    }

    private void herbivoreMove(Herbivore herbivore, Coordinates startCoordinates){
        Class <? extends Entity> goal = herbivore.getGoal();
        if (canMove(goal, herbivore, startCoordinates)){
            Coordinates nextMove = herbivore.makeMove(startCoordinates, gameMap);
            creatureAction.action(startCoordinates, nextMove, herbivore);
        }
    }

    private void predatorMove(Predator predator, Coordinates startCoordinates){
        Class <? extends Entity> goal = predator.getGoal();
        if (canMove(goal, predator, startCoordinates)){
            Coordinates nextMove = predator.makeMove(startCoordinates, gameMap);
            creatureAction.action(startCoordinates, nextMove, predator);
        }
    }

    private boolean isEmpty(Coordinates coordinates){
        return gameMap.isEmpty(coordinates);
    }

    private boolean canMove(Class <? extends Entity> goal, Creature creature, Coordinates startCoordinates){
        return gameMap.isContains(goal)
                && gameMap.isAlive(startCoordinates, creature.getId());
    }
}
