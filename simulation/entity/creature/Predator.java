package simulation.entity.creature;

import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

import java.util.List;

public class Predator extends Creature {

    private final int damage;
    private final int damageRange;

    public Predator() {
        super(parameters.getRandomSpeed(), Herbivore.class);
        this.damage = parameters.getRandomDamage();
        this.damageRange = parameters.getRandomRange();
    }

    public Predator(int speed, int damage, int range) {
        super(speed, Herbivore.class);
        this.damage = damage;
        this.damageRange = range;
    }

    public int getDamage() throws NullPointerException {
        return this.damage;
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, GameMap gameMap) {
        List<Coordinates> path = super.findPath(coordinates, gameMap);
        int pathSize = path.size();
        if (isNoGoal(path, coordinates)) {
            return path.getFirst();
        } else if (damageRange >= pathSize) {
            return path.getLast();
        } else if (pathSize > speed) {
            return path.get(speed - TURN_TO_INDEX);
        } else {
            return path.get(pathSize - TURN_TO_INDEX - RANGE_TO_GOAL);
        }
    }

    public boolean canDamage(Coordinates coordinates, GameMap gameMap) {
        return gameMap.isCoordinatesContain(coordinates, goal);
    }
}
