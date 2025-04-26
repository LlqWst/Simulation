package simulation;

import simulation.entity.creature.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

public class CreatureActivity {
    private final GameMap gameMap;

    public CreatureActivity(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void doActivity(Creature creature, Coordinates startCoordinates){
        Coordinates nextMove = creature.makeMove(startCoordinates, gameMap);
        if (creature instanceof Herbivore herbivore && herbivore.canEat(nextMove, gameMap)) {
            doEat(herbivore, startCoordinates, nextMove);
        } else if (creature instanceof Predator predator && predator.canDamage(nextMove, gameMap)) {
            doDamage(predator, nextMove);
        } else {
            moving(creature, startCoordinates, nextMove);
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
