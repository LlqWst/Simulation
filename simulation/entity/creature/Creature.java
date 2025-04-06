package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.Entity;

public abstract class Creature extends Entity {

    private boolean movedStatus;
    protected static Entity aim;
    public abstract Coordinates makeMove(Coordinates coordinates, Map map);

    public Creature() {
        this.movedStatus = false;
    }

    public boolean currentStatus() {
        return this.movedStatus;
    }

    public void changeStatus() {
        this.movedStatus = !movedStatus;
    }
}
