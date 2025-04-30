package simulation.entity.creature;

import simulation.pathfinder.*;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;
import simulation.entity.static_objects.Grass;

import java.util.List;

public class Herbivore extends Creature {

    private int hp;
    public Herbivore(int speed, int hp, int range, PathFinder pathFinder) {
        super(speed, range, Grass.class, pathFinder);
        this.hp = hp;
    }

    public int getHp() throws NullPointerException {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    private void doEat(GameMap gameMap, Coordinates start, Coordinates end) {
        gameMap.removeEntity(start);
        gameMap.setEntity(end, this);
    }

    public void makeMove(GameMap gameMap){
        Coordinates start = gameMap.getCoordinates(this);
        List<Coordinates> path = this.pathFinder.find(gameMap, start, goal);
        int pathSize = path.size();
        if (super.isReachable(path)) {
            int possibleStep;
            if (super.getGoal(pathSize, this.range)) {
                doEat(gameMap, start, path.getFirst());
            } else {
                possibleStep = Math.min(this.speed - TURN_TO_INDEX, pathSize - TURN_TO_INDEX - STOP_BEFORE_GOAL);
                super.move(gameMap, start, path.get(possibleStep));
            }
        }
    }

}
