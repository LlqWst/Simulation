package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.GameMapUtils;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.*;

public class BfsAlgorithm extends PathFinder {

    @Override
    public List<Coordinates> find(GameMap gameMap, Coordinates start, Class<? extends Entity> goal) {
        if (isThereNoGoal(goal, gameMap)) {
            return Collections.emptyList();
        }
        Queue<Coordinates> forVisit = new ArrayDeque<>();
        Map<Coordinates, Coordinates> visited = new HashMap<>();
        forVisit.add(start);
        visited.put(start, start);
        Coordinates current;
        do {
            current = forVisit.poll();
            if (isThereNoPath(current)) {
                return Collections.emptyList();
            }
            current = seekGoal(current, forVisit, visited,  goal, gameMap);
        } while (!isGoal(current, goal, gameMap));
        return reconstructPath(current, start, visited);
    }

    private Coordinates seekGoal(Coordinates coordinates, Queue<Coordinates> forVisit, Map<Coordinates, Coordinates> visited, Class<? extends Entity> goal, GameMap gameMap) {
        List<Coordinates> neighbours = findNeighbours(coordinates, forVisit, visited,  goal, gameMap);
        for (Coordinates neighbour : neighbours) {
            visited.put(neighbour, coordinates);
            if (isGoal(neighbour, goal, gameMap)) {
                return neighbour;
            }
        }
        forVisit.addAll(neighbours);
        return coordinates;
    }

    private List<Coordinates> findNeighbours(Coordinates current, Queue<Coordinates> forVisit, Map<Coordinates, Coordinates> visited, Class<? extends Entity> goal, GameMap gameMap) {
        List<Coordinates> neighbours = new ArrayList<>();
        int rowShift = -1, columnShift = -1;
        while (isNotLowerRightCell(rowShift, columnShift)) {
            if (isNewRow(columnShift)) {
                columnShift = -1;
                rowShift++;
            }
            int nearRow = current.row() + rowShift;
            int nearColumn = current.column() + columnShift;
            Coordinates coordinates = new Coordinates(nearRow, nearColumn);
            if (!isValidCell(coordinates, forVisit, visited, goal, gameMap)) {
                columnShift++;
                continue;
            }
            neighbours.add(coordinates);
            columnShift++;
        }
        return neighbours;
    }

    private boolean isValidCell(Coordinates coordinates, Queue<Coordinates> forVisit, Map<Coordinates, Coordinates> visited, Class<? extends Entity> goal, GameMap gameMap) {
        return gameMap.isValidCoordinate(coordinates)
                && !forVisit.contains(coordinates)
                && !visited.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || isGoal(coordinates, goal, gameMap));
    }

    private boolean isGoal(Coordinates coordinates, Class<? extends Entity> goal, GameMap gameMap) {
        return GameMapUtils.isCoordinatesContains(gameMap, coordinates, goal);
    }

}
