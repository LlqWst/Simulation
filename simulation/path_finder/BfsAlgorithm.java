package simulation.path_finder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.*;

public class BfsAlgorithm{
    private final GameMap gameMap;
    private final Class<? extends Entity> goal;
    private final Coordinates startCoordinates;

    private final Queue<Coordinates> coordinatesForVisit = new ArrayDeque<>();
    private final Map<Coordinates, Coordinates> visitedCoordinates = new HashMap<>();

    public BfsAlgorithm(Coordinates coordinates, GameMap gameMap, Class<? extends Entity> goal) {
        this.gameMap = gameMap;
        this.goal = goal;
        this.startCoordinates = coordinates;
        coordinatesForVisit.add(coordinates);
        visitedCoordinates.put(coordinates, coordinates);
    }

    public List<Coordinates> findPath() {
        Coordinates currentCoordinates;
        if (!isGoalExists()) {
            return Collections.emptyList();
        }
        do {
            currentCoordinates = coordinatesForVisit.poll();
            if (isThereNoPath(currentCoordinates)) {
                return Collections.emptyList();
            }
            currentCoordinates = seekGoal(currentCoordinates);
        } while (!isGoal(currentCoordinates));
        return findWholePath(currentCoordinates);
    }

    private boolean isThereNoPath(Coordinates coordinates){
        return coordinates == null;
    }

    private boolean isGoalExists() {
        return gameMap.isContains(goal);
    }

    private Coordinates seekGoal(Coordinates coordinates) {
        List<Coordinates> nearCells = findNearCells(coordinates);
        coordinatesForVisit.addAll(nearCells);
        for (Coordinates nearCell : nearCells) {
            visitedCoordinates.put(nearCell, coordinates);
            if (isGoal(nearCell)) {
                return nearCell;
            }
        }
        return coordinates;
    }

    private List<Coordinates> findNearCells(Coordinates currentCoordinates) {
        List<Coordinates> nearCoordinates = new ArrayList<>();
        int rowShift = -1, columnShift = -1;
        while (!isBelowRightCell(rowShift, columnShift)) {
            if (isNewRow(columnShift)) {
                columnShift = -1;
                rowShift++;
            }
            int nearRow = currentCoordinates.row() + rowShift;
            int nearColumn = currentCoordinates.column() + columnShift;
            Coordinates coordinates = new Coordinates(nearRow, nearColumn);
            if (!isValidCell(coordinates)) {
                columnShift++;
                continue;
            }
            nearCoordinates.add(coordinates);
            columnShift++;
        }
        return nearCoordinates;
    }

    private boolean isBelowRightCell(int rowShift, int columnShift) {
        return rowShift == 1 && columnShift > 1;
    }

    private boolean isNewRow(int columnShift) {
        return columnShift > 1;
    }

    private boolean isValidCell(Coordinates coordinates) {
        return gameMap.isValidCoordinate(coordinates)
                && !coordinatesForVisit.contains(coordinates)
                && !visitedCoordinates.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || (isGoal(coordinates)));
    }

    private List<Coordinates> findWholePath(Coordinates goalCoordinates) {
        Coordinates partOfPath = goalCoordinates;
        List<Coordinates> result = new ArrayList<>();
        do {
            result.add(partOfPath);
            partOfPath = visitedCoordinates.get(partOfPath);
        } while (!partOfPath.equals(startCoordinates));
        return result.reversed();
    }

    private boolean isGoal(Coordinates coordinates) {
        return gameMap.isCoordinatesContains(coordinates, goal);
    }

}
