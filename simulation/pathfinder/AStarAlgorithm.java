package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.*;

import static java.lang.Math.abs;

public class AStarAlgorithm{

    private final static int DIAGONAL_LENGTH = 14;
    private final static int ORTHOGONAL_LENGTH = 10;
    private final static int HEURISTIC_MULTIPLY = 10;

    private final GameMap gameMap;
    private final Class<? extends Entity> goal;
    private final Coordinates startCoordinates;
    private final List<Coordinates> allGoalsCoordinates;
    private Queue<Node> nodesForVisit;
    private Coordinates goalCoordinates;
    private final List<Node> visited = new ArrayList<>();

    public AStarAlgorithm(Coordinates coordinates, GameMap gameMap, Class<? extends Entity> goal) {
        this.gameMap = gameMap;
        this.goal = goal;
        this.startCoordinates = coordinates;
        this.allGoalsCoordinates = gameMap.getEntitiesCoordinates(goal);
    }

    private int heuristic(Coordinates coordinates){
        if(coordinates == null){
            throw new IllegalArgumentException();
        }
        return HEURISTIC_MULTIPLY * (abs(goalCoordinates.row() -  coordinates.row()) + abs(goalCoordinates.column() - coordinates.column()));
    }

    public List<Coordinates> findPath() {
        if (!isGoalExists()) {
            return Collections.emptyList();
        }
        nodesForVisit = new PriorityQueue<>(Comparator.comparingInt(Node::cellAmount));
        int minLengthToGoal = 0;
        List<Coordinates> wholePath = Collections.emptyList();
        for(Coordinates coordinatesOfGoal : allGoalsCoordinates) {
            resetCollections();
            goalCoordinates = coordinatesOfGoal;
            Coordinates currentCoordinates = searchForShorterPath();
            if(isNotReachable(currentCoordinates)){
                continue;
            }
            int lengthToCurrentGoal = findLengthToGoal(currentCoordinates);
            if (isShorterWay(lengthToCurrentGoal, minLengthToGoal)) {
                minLengthToGoal = lengthToCurrentGoal;
                wholePath = findWholePath(currentCoordinates);
            }
        }
        return wholePath;
    }

    private boolean isGoalExists() {
        return gameMap.isContains(goal);
    }

    private void resetCollections(){
        nodesForVisit.clear();
        visited.clear();
        nodesForVisit.add(new Node(startCoordinates, startCoordinates, 0, 0));
    }

    private Coordinates searchForShorterPath(){
        Coordinates currentCoordinates = null;
        do {
            Node currentNode = nodesForVisit.poll();
            if (isThereNoPath(currentNode)) {
                break;
            }
            currentCoordinates = currentNode.coordinates();
            visited.add(currentNode);
            currentCoordinates = seekGoal(currentCoordinates, currentNode);
        } while (!isGoal(currentCoordinates));
        return currentCoordinates;
    }

    private boolean isThereNoPath(Node currentCell){
        return currentCell == null;
    }

    private Coordinates seekGoal(Coordinates coordinates, Node parent) {
        Map<Coordinates, Integer> nearCells = findNearCells(coordinates);
        for(Coordinates nearCell : nearCells.keySet()){
            int length = nearCells.get(nearCell);
            int pathLength = length + parent.pathLength();
            int cellAmount = pathLength + heuristic(nearCell);
            Node node = new Node(nearCell, parent.coordinates(), pathLength, cellAmount);
            if(!isNodeExist(node)){
                nodesForVisit.add(node);
            } else if (canChangeLength(node, pathLength)){
                nodesForVisit.remove(node);
                nodesForVisit.add(node);
            }
            if(isGoal(nearCell)){
                visited.add(node);
                return nearCell;
            }
        }
        return coordinates;
    }

    private Map<Coordinates, Integer> findNearCells(Coordinates currentCoordinates) {
        Map<Coordinates, Integer> nearCoordinates = new HashMap<>();
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

            int size = ORTHOGONAL_LENGTH;
            if(isDiagonal(rowShift, columnShift)){
                size = DIAGONAL_LENGTH;
            }

            nearCoordinates.put(coordinates, size);
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
                && !isVisited(coordinates)
                && (gameMap.isEmpty(coordinates) || isGoal(coordinates));
    }

    private boolean isVisited(Coordinates coordinates){
        for(Node node : visited){
            if(node.coordinates().equals(coordinates)){
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonal(int rowShift, int columnShift){
        return (columnShift == -1 || columnShift == 1) && (rowShift == -1 || rowShift == 1);
    }

    private boolean isNodeExist(Node node){
        return nodesForVisit.contains(node);
    }

    private boolean canChangeLength(Node node, int newLength){
        return isNodeExist(node) && nodeLength(node) > newLength;
    }

    private int nodeLength(Node node){
        for(Node entry : nodesForVisit){
            if(entry.equals(node)) {
                return entry.pathLength();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean isNotReachable(Coordinates coordinates){
        return coordinates == null || !isGoal(coordinates);
    }

    private boolean isGoal(Coordinates coordinates) {
        if(coordinates == null){
            throw new IllegalArgumentException();
        }
        return coordinates.equals(goalCoordinates);
    }

    private int findLengthToGoal(Coordinates coordinates){
        if(coordinates == null){
            throw new IllegalArgumentException();
        }
        for(Node entry : visited){
            if (entry.coordinates().equals(coordinates)){
                return entry.pathLength();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean isShorterWay(int lengthToCurrentGoal, int minLengthToGoal){
        return minLengthToGoal == 0 || minLengthToGoal > lengthToCurrentGoal;
    }

    private List<Coordinates> findWholePath(Coordinates goalCoordinates) {
        Coordinates partOfPath = goalCoordinates;
        List<Coordinates> result = new ArrayList<>();
        do {
            result.add(partOfPath);
            partOfPath = findParent(partOfPath);
        } while (!partOfPath.equals(startCoordinates));
        return result.reversed();
    }

    private Coordinates findParent(Coordinates coordinates){
        for(Node node : visited){
            if(node.coordinates().equals(coordinates)){
                return node.parent();
            }
        }
        throw new IllegalArgumentException();
    }

}

