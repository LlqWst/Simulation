package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.*;
import simulation.entity.staticObjects.Grass;

public class Herbivore extends Creature {

    private boolean movedStatus;

    public Herbivore() {
        super();
    }

    protected static final Entity aim = new Grass();
    @Override
    public Coordinates makeMove(Coordinates coordinates, Map map){
        return new FindPath(coordinates, map, aim).findPath();
    }

}
