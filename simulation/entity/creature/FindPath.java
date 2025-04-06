package simulation.entity.creature;

import simulation.Coordinates;
import simulation.Map;
import simulation.entity.*;

import java.util.*;

public class FindPath {
    private final Map map;
    private final Entity aim;
    private final Coordinates startCoordinates;

    private final Queue<Coordinates> coordinatesForVisited = new ArrayDeque<>();
    private final java.util.Map<Coordinates, ParentsDistanceForCoordinates> visitedCoordinates = new HashMap<>();

    public FindPath(Coordinates coordinates, Map map, Entity aim) {
        this.map = map;
        this.aim = aim;
        this.startCoordinates = coordinates;
        coordinatesForVisited.add(coordinates);
        visitedCoordinates.put(coordinates, new ParentsDistanceForCoordinates(0, coordinates));
    }

    public Coordinates findPath(){

        int distance = 0;
        Coordinates currentCell;

        if(!map.getAllEntities().containsValue(aim)){
            return startCoordinates;
        }

        do  {
            currentCell = coordinatesForVisited.poll();
            if(currentCell == null){
                return startCoordinates;
            }

            ArrayList<Coordinates> nearCells = findNearCells(currentCell);
            coordinatesForVisited.addAll(nearCells);
            for (Coordinates nearCell : nearCells){
                visitedCoordinates.put(nearCell, new ParentsDistanceForCoordinates(distance, currentCell));
                if(!map.isEmpty(nearCell) && map.getEntities(nearCell).getClass() == aim.getClass()){
                    currentCell = nearCell;
                    break;
                }
            }
            distance++;
            //System.out.println(currentCell.getRow() + " " + currentCell.getColumn());
        } while (map.isEmpty(currentCell) || map.getEntities(currentCell).getClass() != aim.getClass());

//        for (HashMap.Entry<Coordinates, ParentsDistanceForCoordinates> coordinates123 : visitedCoordinates.entrySet()){
//            System.out.println(coordinates123.getKey().getRow() +" "+ coordinates123.getKey().getColumn() +" "+ coordinates123.getValue().getDistance() +" "+ coordinates123.getValue().getParents().getRow()+" "+ coordinates123.getValue().getParents().getColumn());
//        }
        List<Coordinates> wholePath = findWholePath(currentCell);

//        for(Coordinates coordinates: wholePath){
//            System.out.println("row: " + coordinates.getRow() + " column: " + coordinates.getColumn());
//        }


        return wholePath.getFirst();
    }

    private ArrayList<Coordinates> findNearCells(Coordinates currentCell){
        ArrayList<Coordinates> nearCells = new ArrayList<>();
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

//        for (Coordinates coordinates1 : nearCells){
//            System.out.println("row: " + coordinates1.getRow() + " column:" + coordinates1.getColumn());
//        }

        return nearCells;
    }

    private boolean isValidCell(Coordinates coordinates){
        return coordinates.getRow() >= 0 && coordinates.getColumn() >= 0
                && coordinates.getRow() <= map.getMaxRow() - 1 && coordinates.getColumn() <= map.getMaxColumn() - 1
//                && !(map.getEntities(coordinates) instanceof Tree)
//                && !(map.getEntities(coordinates) instanceof Rock)
//                && !(map.getEntities(coordinates) instanceof Predator)
                && !coordinatesForVisited.contains(coordinates)
                && !visitedCoordinates.containsKey(coordinates)
                && (map.isEmpty(coordinates) || (!map.isEmpty(coordinates) && map.getEntities(coordinates).equals(aim)));
    }

    private List<Coordinates> findWholePath(Coordinates aimCell){

        Coordinates pathPart = aimCell;
        List<Coordinates> result = new ArrayList<>();
        do {
           result.add(pathPart);
           pathPart = visitedCoordinates.get(pathPart).getParents();
        } while(!pathPart.equals(startCoordinates));

        return result.reversed();
    }

}
