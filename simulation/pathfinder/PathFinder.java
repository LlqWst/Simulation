package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class PathFinder {

    protected final GameMap gameMap;
    protected final Class<? extends Entity> goal;
    protected final Coordinates startCoordinates;
    protected final Map<Coordinates, Coordinates> visitedCoordinates;

    protected PathFinder(GameMap gameMap, Class<? extends Entity> goal, Coordinates startCoordinates, Map<Coordinates, Coordinates> visitedCoordinates) {
        this.gameMap = gameMap;
        this.goal = goal;
        this.startCoordinates = startCoordinates;
        this.visitedCoordinates = visitedCoordinates;
    }

    abstract public List<Coordinates> findPath();

    protected List<Coordinates> findWholePath(Coordinates goalCoordinates) {
        Coordinates partOfPath = goalCoordinates;
        List<Coordinates> result = new ArrayList<>();
        do {
            result.add(partOfPath);
            partOfPath = visitedCoordinates.get(partOfPath);
        } while (!partOfPath.equals(startCoordinates));
        return result.reversed();
    }

}
