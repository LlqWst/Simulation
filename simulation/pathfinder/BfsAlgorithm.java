package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.*;

public class BfsAlgorithm extends PathFinder{

    private final Queue<Coordinates> coordinatesForVisit = new ArrayDeque<>();

    public BfsAlgorithm(Coordinates start, GameMap gameMap, Class<? extends Entity> goal) {
        super(gameMap, goal, start, new HashMap<>());
    }

    @Override
    public List<Coordinates> findPath() {
        coordinatesForVisit.add(startCoordinates);
        visitedCoordinates.put(startCoordinates, startCoordinates);
        Coordinates currentCoordinates;
        if (isThereNoGoal()) {
            return Collections.emptyList();
        }
        do {
            currentCoordinates = coordinatesForVisit.poll();
            if (isThereNoPath(currentCoordinates)) {
                return Collections.emptyList();
            }
            currentCoordinates = seekGoal(currentCoordinates);
        } while (!isGoal(currentCoordinates));
        return reconstructPath(currentCoordinates);
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
        while (isNotLowerRightCell(rowShift, columnShift)) {
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

    private boolean isValidCell(Coordinates coordinates) {
        return gameMap.isValidCoordinate(coordinates)
                && !coordinatesForVisit.contains(coordinates)
                && !visitedCoordinates.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || (isGoal(coordinates)));
    }

    private boolean isGoal(Coordinates coordinates) {
        return gameMap.isCoordinatesContains(coordinates, goal);
    }

}
