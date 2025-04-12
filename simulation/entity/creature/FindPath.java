package simulation.entity.creature;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.entity.*;

import java.util.*;

public class FindPath {
    private final GameMap gameMap;
    private final Entity goal;
    private final Coordinates startCoordinates;

    private final Queue<Coordinates> coordinatesForVisited = new ArrayDeque<>();
    private final Map<Coordinates, Coordinates> visitedCoordinates = new HashMap<>();

    public FindPath(Coordinates coordinates, GameMap gameMap, Entity goal) {
        this.gameMap = gameMap;
        this.goal = goal;
        this.startCoordinates = coordinates;
        coordinatesForVisited.add(coordinates);
        visitedCoordinates.put(coordinates, coordinates);
    }

    public List<Coordinates> findPath(){

        Coordinates currentCell;

        if(!gameMap.getAllEntities().containsValue(goal)){
            return Collections.singletonList(startCoordinates);
        }

        do  {
            currentCell = coordinatesForVisited.poll();
            if(currentCell == null){
                return Collections.singletonList(startCoordinates);
            }

            List<Coordinates> nearCells = findNearCells(currentCell);
            coordinatesForVisited.addAll(nearCells);
            for (Coordinates nearCell : nearCells){
                visitedCoordinates.put(nearCell, currentCell);
                if(!gameMap.isEmpty(nearCell) && gameMap.getEntities(nearCell).getClass() == goal.getClass()){
                    currentCell = nearCell;
                    break;
                }
            }
            //System.out.println(currentCell.getRow() + " " + currentCell.getColumn());
        } while (gameMap.isEmpty(currentCell) || gameMap.getEntities(currentCell).getClass() != goal.getClass());

//        for (HashMap.Entry<Coordinates, ParentsDistanceForCoordinates> coordinates123 : visitedCoordinates.entrySet()){
//            System.out.println(coordinates123.getKey().getRow() +" "+ coordinates123.getKey().getColumn() +" "+ coordinates123.getValue().getDistance() +" "+ coordinates123.getValue().getParents().getRow()+" "+ coordinates123.getValue().getParents().getColumn());
//        }

        return findWholePath(currentCell);

//        for(Coordinates coordinates: wholePath){
//            System.out.println("row: " + coordinates.getRow() + " column: " + coordinates.getColumn());
//        }


    }

    private List<Coordinates> findNearCells(Coordinates currentCell){
        List<Coordinates> nearCells = new ArrayList<>();
        int rowShift = -1, columnShift = -1;
        while(rowShift < 1 || columnShift <= 1) {
            if(columnShift > 1){
                columnShift = -1;
                rowShift++;
            }
//            for (Coordinates coordinates1 : nearCells){
//                System.out.println("row: " + coordinates1.getRow() + " column:" + coordinates1.getColumn());
//            }

            Coordinates coordinates = new Coordinates(currentCell.getRow() + rowShift,
                    currentCell.getColumn() + columnShift);

            if(!isValidCell(coordinates)){
                columnShift++;
                continue;
            }
            nearCells.add(coordinates);
            columnShift++;
        }
        return nearCells;
//        for (Coordinates coordinates1 : nearCells){
//            System.out.println("row: " + coordinates1.getRow() + " column:" + coordinates1.getColumn());
//        }


    }

    private boolean isValidCell(Coordinates coordinates){
        return coordinates.getRow() >= 0 && coordinates.getColumn() >= 0
                && coordinates.getRow() <= gameMap.getMaxRow() - 1 && coordinates.getColumn() <= gameMap.getMaxColumn() - 1
                && !coordinatesForVisited.contains(coordinates)
                && !visitedCoordinates.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || (!gameMap.isEmpty(coordinates) && gameMap.getEntities(coordinates).equals(goal)));
    }

    private List<Coordinates> findWholePath(Coordinates aimCell){

        Coordinates pathPart = aimCell;
        List<Coordinates> result = new ArrayList<>();
        do {
           result.add(pathPart);
           pathPart = visitedCoordinates.get(pathPart);
        } while(!pathPart.equals(startCoordinates));

        return result.reversed();
    }

}
