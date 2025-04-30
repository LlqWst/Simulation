package simulation.entity.creature;

import simulation.pathfinder.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;

    public Predator(int speed, int damage, int range, PathFinder pathFinder) {
        super(speed, range, Herbivore.class, pathFinder);
        this.damage = damage;
    }

    public int getDamage() throws NullPointerException {
        return this.damage;
    }

    @Override
    public void makeMove(GameMap gameMap) {
        Coordinates start = gameMap.getCoordinates(this);
        List<Coordinates> path = this.pathFinder.find(gameMap, start, goal);
        int pathSize = path.size();
        if (super.isReachable(path)) {
            int possibleStep;
            if (super.getGoal(pathSize, this.range)) {
                doDamage(gameMap, path.getFirst());
            } else {
                possibleStep = Math.min(this.speed - TURN_TO_INDEX, pathSize - TURN_TO_INDEX - STOP_BEFORE_GOAL);
                super.move(gameMap, start, path.get(possibleStep));
            }
        }
    }

    private void doDamage(GameMap gameMap, Coordinates hrbCoordinates) {
        Herbivore hrb = ((Herbivore) gameMap.getEntity(hrbCoordinates));
        int hp = hrb.getHp() - getDamage();
        hrb.setHp(hp);
        if (super.isDead(hp)) {
            gameMap.removeEntity(hrbCoordinates);
        }
    }

}
