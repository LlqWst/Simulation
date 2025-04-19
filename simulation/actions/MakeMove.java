package simulation.actions;

import simulation.PrintMoves;
import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;

import java.util.HashMap;
import java.util.Map;

public class MakeMove extends Actions{
    private final GameMap gameMap;
    private final CreatureActivity creatureAction;
    private final PrintMoves printMoves;


    public MakeMove(GameMap gameMap, CreatureActivity creatureAction, PrintMoves printMoves) {
        this.gameMap = gameMap;
        this.creatureAction = creatureAction;
        this.printMoves = printMoves;
    }

    @Override
    public void execute () {
        HashMap<Coordinates, Creature> creatures = gameMap.getCreatures();
        for (Map.Entry<Coordinates, Creature> entry : creatures.entrySet()){
            Coordinates startCoordinates = entry.getKey();
            Creature creature = entry.getValue();
            Class <? extends Entity> goal = creature.getGoal();
            if (canMove(goal, creature, startCoordinates)){
                creatureMoves(creature, startCoordinates);
            }
        }
    }

    private void creatureMoves(Creature creature, Coordinates startCoordinates){
        Coordinates nextMove = creature.makeMove(startCoordinates, gameMap);
        if(creature instanceof Herbivore herbivore){
            herbivoreMoves(herbivore, startCoordinates, nextMove);
        } else if(creature instanceof Predator predator){
            predatorMoves(predator, startCoordinates, nextMove);
        } else throw new IllegalArgumentException();
    }

    private void predatorMoves(Predator predator, Coordinates startCoordinates, Coordinates nextMove){
        if(predator.canDamage(nextMove, gameMap)){
            creatureAction.doDamage(nextMove, predator);
        } else {
            moving(predator, startCoordinates, nextMove);
        }
        printMoves.print(predator, startCoordinates, nextMove);
    }

    private void herbivoreMoves(Herbivore herbivore, Coordinates startCoordinates, Coordinates nextMove){
        printMoves.print(herbivore, startCoordinates, nextMove);
        if(herbivore.canEat(nextMove, gameMap)){
            creatureAction.doEat(startCoordinates, nextMove, herbivore);
        } else {
            moving(herbivore, startCoordinates, nextMove);
        }
    }

    private void moving(Creature creature, Coordinates startCoordinates, Coordinates nextMove){
        gameMap.removeEntity(startCoordinates);
        gameMap.setEntity(nextMove, creature);
    }

    private boolean canMove(Class <? extends Entity> goal, Creature creature, Coordinates startCoordinates){
        return !gameMap.isEmpty(startCoordinates)
                && gameMap.isContains(goal)
                && gameMap.isAlive(startCoordinates, creature.getId());
    }
}
