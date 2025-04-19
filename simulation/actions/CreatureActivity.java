package simulation.actions;

import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

public class CreatureActivity {
    private final GameMap gameMap;

    public CreatureActivity(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void doEat(Coordinates startCoordinates, Coordinates nextMove, Herbivore herbivore){
        gameMap.removeEntity(startCoordinates);
        gameMap.setEntity(nextMove, herbivore);
    }

    public void doDamage(Coordinates hrbCoordinates, Predator predator){
        Herbivore hrb = ((Herbivore) gameMap.getEntity(hrbCoordinates));
        int hp = hrb.getHp() - predator.getDamage();
        hrb.setHp(hp);
        if (isDead(hp)) {
            gameMap.removeEntity(hrbCoordinates);
        }
    }

    private boolean isDead(int hp){
        return hp <= 0;
    }
}
