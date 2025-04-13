package simulation;

import simulation.entity.Entity;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

import java.util.*;

public class PathFinder {
    private final GameMap gameMap;
    private final Entity goal;
    private final Coordinates startCoordinates;

    private final Queue<Coordinates> coordinatesForVisited = new ArrayDeque<>();
    private final Map<Coordinates, Coordinates> visitedCoordinates = new HashMap<>();

    public PathFinder(Coordinates coordinates, GameMap gameMap, Entity goal) {
        this.gameMap = gameMap;
        this.goal = goal;
        this.startCoordinates = coordinates;
        coordinatesForVisited.add(coordinates);
        visitedCoordinates.put(coordinates, coordinates);
    }

    public List<Coordinates> findPath(){
        Coordinates currentCoordinates;

        if(!gameMap.isContains(goal)){
            return Collections.singletonList(startCoordinates);
        }
        do  {
            currentCoordinates = coordinatesForVisited.poll();
            if(currentCoordinates == null){
                return Collections.singletonList(startCoordinates);
            }

            List<Coordinates> nearCells = findNearCells(currentCoordinates);
            coordinatesForVisited.addAll(nearCells);
            for (Coordinates nearCell : nearCells){
                visitedCoordinates.put(nearCell, currentCoordinates);
                if(isGoal(nearCell)){
                    currentCoordinates = nearCell;
                    break;
                }
            }
        } while (!isGoal(currentCoordinates));

        return findWholePath(currentCoordinates);
    }

    private List<Coordinates> findNearCells(Coordinates currentCoordinates){
        List<Coordinates> nearCoordinates = new ArrayList<>();
        int rowShift = -1, columnShift = -1;
        while(rowShift < 1 || columnShift <= 1) {
            if(columnShift > 1){
                columnShift = -1;
                rowShift++;
            }

            int nearRow = currentCoordinates.row() + rowShift;
            int nearColumn = currentCoordinates.column() + columnShift;
            Coordinates coordinates = new Coordinates(nearRow,nearColumn);

            if(!isValidCell(coordinates)){
                columnShift++;
                continue;
            }
            nearCoordinates.add(coordinates);
            columnShift++;
        }
        return nearCoordinates;
    }

    private boolean isValidCell(Coordinates coordinates){
        return coordinates.row() >= 0 && coordinates.column() >= 0
                && coordinates.row() <= gameMap.getMaxRow() - 1
                && coordinates.column() <= gameMap.getMaxColumn() - 1
                && !coordinatesForVisited.contains(coordinates)
                && !visitedCoordinates.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || (isGoal(coordinates)));
    }

    private List<Coordinates> findWholePath(Coordinates aimCoordinates){

        Coordinates pathPart = aimCoordinates;
        List<Coordinates> result = new ArrayList<>();
        do {
            result.add(pathPart);
            pathPart = visitedCoordinates.get(pathPart);
        } while(!pathPart.equals(startCoordinates));

        return result.reversed();
    }

    private boolean isGoal (Coordinates coordinates){
        return !gameMap.isEmpty(coordinates) && gameMap.getEntities(coordinates).equals(goal);
    }

}
