package simulation;

import simulation.entity.creature.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;
import simulation.handler.PrintMoves;

public class CreatureActivity {
    private final GameMap gameMap;
    private final PrintMoves printMoves;

    public CreatureActivity(GameMap gameMap) {
        this.gameMap = gameMap;
        this.printMoves = new PrintMoves(gameMap);
    }

    public void doActivity(Creature creature, Coordinates startCoordinates){
        Coordinates nextMove = creature.makeMove(startCoordinates, gameMap);
        if (creature instanceof Herbivore herbivore && herbivore.canEat(nextMove, gameMap)) {
            printMoves.print(herbivore, startCoordinates, nextMove);
            doEat(herbivore, startCoordinates, nextMove);
        } else if (creature instanceof Predator predator && predator.canDamage(nextMove, gameMap)) {
            doDamage(predator, nextMove);
            printMoves.print(predator, startCoordinates, nextMove);
        } else {
            moving(creature, startCoordinates, nextMove);
            printMoves.printAll(creature, startCoordinates, nextMove);
        }
    }

    private void doEat(Herbivore herbivore, Coordinates startCoordinates, Coordinates nextMove) {
        gameMap.removeEntity(startCoordinates);
        gameMap.setEntity(nextMove, herbivore);
    }

    private void doDamage(Predator predator, Coordinates hrbCoordinates) {
        Herbivore hrb = ((Herbivore) gameMap.getEntity(hrbCoordinates));
        int hp = hrb.getHp() - predator.getDamage();
        hrb.setHp(hp);
        if (isDead(hp)) {
            gameMap.removeEntity(hrbCoordinates);
        }
    }

    private void moving(Creature creature, Coordinates startCoordinates, Coordinates nextMove) {
        gameMap.removeEntity(startCoordinates);
        gameMap.setEntity(nextMove, creature);
    }

    private boolean isDead(int hp) {
        return hp <= 0;
    }
}
