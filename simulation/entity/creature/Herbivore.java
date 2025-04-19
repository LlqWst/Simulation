package simulation.entity.creature;

import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;
import simulation.entity.staticObjects.Grass;

import java.util.List;

public class Herbivore extends Creature {

    private int hp;

    public Herbivore() {
        super(parameters.getRandomSpeed(), Grass.class);
        this.hp = parameters.getRandomHp();
    }

    public Herbivore(int speed, int hp, int id) {
        super(speed, Grass.class);
        this.hp = hp;
        this.id = id;
    }

    public int getHp() throws NullPointerException {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, GameMap gameMap) {
        List<Coordinates> path = super.findPath(coordinates, gameMap);
        int pathSize = path.size();
        if (shouldEat(pathSize) || isNoGoal(path, coordinates)) {
            return path.getFirst();
        } else if (pathSize > speed) {
            return path.get(speed - TURN_TO_INDEX);
        } else {
            return path.get(pathSize - TURN_TO_INDEX - RANGE_TO_GOAL);
        }
    }

    private boolean shouldEat(int rangeToGrass) {
        return rangeToGrass == RANGE_TO_GOAL;
    }

    public boolean canEat(Coordinates coordinates, GameMap gameMap) {
        return gameMap.isCoordinatesContain(coordinates, goal);
    }
}
