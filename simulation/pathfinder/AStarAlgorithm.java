package simulation.pathfinder;

import simulation.entity.Entity;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

import java.util.*;

import static java.lang.Math.abs;

public class AStarAlgorithm extends PathFinder{

    private final static int DIAGONAL_WEIGHT = 14;
    private final static int ORTHOGONAL_WEIGHT = 10;
    private final static int HEURISTIC_WEIGHT = 10;

    private final List<Coordinates> allGoalsCoordinates;
    private Queue<Node> nodesForVisit;
    private Coordinates goalCoordinates;

    public AStarAlgorithm(Coordinates coordinates, GameMap gameMap, Class<? extends Entity> goal) {
        super(gameMap, goal, coordinates, new HashMap<>());
        this.allGoalsCoordinates = gameMap.getEntitiesCoordinates(goal);
    }

    private int heuristic(Coordinates coordinates){
        if(coordinates == null){
            throw new IllegalArgumentException();
        }
        return HEURISTIC_WEIGHT * (abs(goalCoordinates.row() -  coordinates.row()) + abs(goalCoordinates.column() - coordinates.column()));
    }

    @Override
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
            Node currentNode = searchForShorterPath();
            if(isNotReachable(currentNode)){
                continue;
            }
            int lengthToCurrentGoal = currentNode.pathLength();
            if (isShorterWay(lengthToCurrentGoal, minLengthToGoal)) {
                minLengthToGoal = lengthToCurrentGoal;
                wholePath = findWholePath(currentNode.coordinates());
            }
        }

        return wholePath;
    }

    private boolean isGoalExists() {
        return gameMap.isContains(goal);
    }

    private void resetCollections(){
        nodesForVisit.clear();
        visitedCoordinates.clear();
        nodesForVisit.add(new Node(startCoordinates, startCoordinates, 0, 0));
    }

    private Node searchForShorterPath(){
        Node currentNode;
        do {
            currentNode = nodesForVisit.poll();
            if (isThereNoWay(currentNode)) {
                break;
            }
            currentNode = seekGoal(currentNode);
        } while (!isGoal(currentNode.coordinates()));
        return currentNode;
    }

    private boolean isNotReachable(Node currentCell){
        return currentCell == null || !isGoal(currentCell.coordinates());
    }

    private boolean isThereNoWay(Node currentCell){
        return currentCell == null;
    }

    private Node seekGoal(Node parent) {
        Coordinates parentCoordinates = parent.coordinates();
        visitedCoordinates.put(parentCoordinates, parent.parent());
        Map<Coordinates, Integer> nearCells = findNearCells(parentCoordinates);
        for(Coordinates nearCell : nearCells.keySet()){
            int weight = nearCells.get(nearCell);
            int pathLength = weight + parent.pathLength();
            int cellAmount = pathLength + heuristic(nearCell);
            Node node = new Node(nearCell, parentCoordinates, pathLength, cellAmount);
            if(!isNodeExist(node)){
                nodesForVisit.add(node);
            } else if (canChangeLength(node, pathLength)){
                nodesForVisit.remove(node);
                nodesForVisit.add(node);
            }
            if(isGoal(nearCell)){
                visitedCoordinates.put(nearCell, parentCoordinates);
                return node;
            }
        }
        return parent;
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

            int length = ORTHOGONAL_WEIGHT;
            if(isDiagonal(rowShift, columnShift)){
                length = DIAGONAL_WEIGHT;
            }

            nearCoordinates.put(coordinates, length);
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
        return visitedCoordinates.containsKey(coordinates);
    }

    private boolean isDiagonal(int rowShift, int columnShift){
        return (columnShift == -1 || columnShift == 1) && (rowShift == -1 || rowShift == 1);
    }

    private boolean isNodeExist(Node node){
        return nodesForVisit.contains(node);
    }

    private boolean canChangeLength(Node node, int newLength){
        return isNodeExist(node) && getNodeLength(node) > newLength;
    }

    private int getNodeLength(Node node){
        for(Node entry : nodesForVisit){
            if(entry.equals(node)) {
                return entry.pathLength();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean isGoal(Coordinates coordinates) {
        return coordinates.equals(goalCoordinates);
    }

    private boolean isShorterWay(int lengthToCurrentGoal, int minLengthToGoal){
        return minLengthToGoal == 0 || minLengthToGoal > lengthToCurrentGoal;
    }

}

