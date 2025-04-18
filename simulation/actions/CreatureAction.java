package simulation.actions;

import simulation.PrintMoves;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

public class CreatureAction{
    private final GameMap gameMap;
    private final PrintMoves printMoves;

    public CreatureAction(GameMap gameMap, PrintMoves printMoves) {
        this.gameMap = gameMap;
        this.printMoves = printMoves;
    }

    public void action(Coordinates startCoordinates, Coordinates nextMove, Herbivore herbivore){
        printMoves.print(herbivore, startCoordinates, nextMove);
        doMove(herbivore, startCoordinates, nextMove);
    }

    private void doMove(Creature creature, Coordinates startCoordinates, Coordinates nextMove){
        gameMap.removeEntity(startCoordinates);
        gameMap.setEntity(nextMove, creature);
    }

    public void action(Coordinates startCoordinates, Coordinates nextMove, Predator predator){
        if(predator.canDamage(nextMove, gameMap)){
            predatorDoDamage(nextMove, predator);
        } else {
            doMove(predator, startCoordinates, nextMove);
        }
        printMoves.print(predator, startCoordinates, nextMove);
    }

    private void predatorDoDamage (Coordinates hrbCoordinates, Predator predator){
        Herbivore hrb = ((Herbivore) gameMap.getEntity(hrbCoordinates));
        int hp = hrb.getHp() - predator.doDamage();
        hrb.setHp(hp);
        if (isDead(hp)) {
            gameMap.removeEntity(hrbCoordinates);
        }
    }

    private boolean isDead(int hp){
        return hp <= 0;
    }
}
