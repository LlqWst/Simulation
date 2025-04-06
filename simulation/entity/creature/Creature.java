package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

import java.util.HashMap;

public abstract class Creature extends Entity {

    protected static Entity aim;
    public abstract Coordinates makeMove(Coordinates coordinates, Map map);



}
