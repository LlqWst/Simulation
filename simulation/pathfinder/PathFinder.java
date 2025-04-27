package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PathFinder {

    abstract public List<Coordinates> find(Coordinates start, Class<? extends Entity> goal, GameMap gameMap);

    protected boolean isThereNoGoal(Class<? extends Entity> goal, GameMap gameMap) {
        return !gameMap.isContains(goal);
    }

    protected boolean isNotLowerRightCell(int rowShift, int columnShift) {
        return rowShift != 1 || columnShift <= 1;
    }

    protected boolean isThereNoPath(Object object){
        return object == null;
    }

    protected boolean isNewRow(int columnShift) {
        return columnShift > 1;
    }

    protected List<Coordinates> reconstructPath(Coordinates goalCoordinates, Coordinates start, Map<Coordinates, Coordinates> visited) {
        Coordinates partOfPath = goalCoordinates;
        List<Coordinates> result = new ArrayList<>();
        do {
            result.add(partOfPath);
            partOfPath = visited.get(partOfPath);
        } while (!partOfPath.equals(start));
        return result.reversed();
    }

}
