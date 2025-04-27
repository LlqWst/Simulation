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

    private int heuristic(Coordinates coordinates, Coordinates currentGoal){
        if(coordinates == null){
            throw new IllegalArgumentException();
        }
        return HEURISTIC_WEIGHT * (abs(currentGoal.row() -  coordinates.row()) + abs(currentGoal.column() - coordinates.column()));
    }

    @Override
    public List<Coordinates> find(Coordinates start, Class<? extends Entity> goal, GameMap gameMap) {
        if (isThereNoGoal(goal, gameMap)) {
            return Collections.emptyList();
        }
        Queue<Node> forVisit = new PriorityQueue<>(Comparator.comparingInt(Node::cellAmount));
        Map<Coordinates, Coordinates> visited = new HashMap<>();
        List<Coordinates> allGoals = gameMap.getEntitiesCoordinates(goal);
        int minLengthToGoal = 0;
        List<Coordinates> wholePath = Collections.emptyList();
        for(Coordinates currentGoal : allGoals) {
            reset(forVisit, visited, start);
            Node goalNode = searchForPath(forVisit, visited, currentGoal, gameMap);
            if(isNotReachable(goalNode, currentGoal)){
                continue;
            }
            int lengthToGoal = goalNode.pathLength();
            if (isShorterWay(lengthToGoal, minLengthToGoal)) {
                minLengthToGoal = lengthToGoal;
                wholePath = reconstructPath(goalNode.coordinates(), start, visited);
            }
        }
        return wholePath;
    }

    private void reset(Queue<Node> forVisit, Map<Coordinates, Coordinates> visited, Coordinates start){
        forVisit.clear();
        visited.clear();
        forVisit.add(new Node(start, start, 0, 0));
    }

    private Node searchForPath(Queue<Node> forVisit, Map<Coordinates, Coordinates> visited, Coordinates currentGoal, GameMap gameMap){
        Node currentNode;
        do {
            currentNode = forVisit.poll();
            if (isThereNoPath(currentNode)) {
                break;
            }
            currentNode = seekGoal(forVisit, visited, currentNode, currentGoal, gameMap);
        } while (!isGoal(currentNode.coordinates(), currentGoal));
        return currentNode;
    }

    private boolean isNotReachable(Node currentCell, Coordinates currentGoal){
        return currentCell == null || !isGoal(currentCell.coordinates(), currentGoal);
    }

    private Node seekGoal(Queue<Node> forVisit, Map<Coordinates, Coordinates> visited, Node current, Coordinates currentGoal, GameMap gameMap) {
        Coordinates currentCoordinates = current.coordinates();
        visited.put(currentCoordinates, current.parent());
        Map<Coordinates, Integer> neighbours = findNeighbours(visited, currentCoordinates, currentGoal, gameMap);
        for(Coordinates neighbour : neighbours.keySet()){
            int weight = neighbours.get(neighbour);
            int pathLength = weight + current.pathLength();
            int cellAmount = pathLength + heuristic(neighbour, currentGoal);
            Node node = new Node(neighbour, currentCoordinates, pathLength, cellAmount);
            if(!isNodeExist(node, forVisit)){
                forVisit.add(node);
            } else if (canChangeLength(node, pathLength, forVisit)){
                forVisit.remove(node);
                forVisit.add(node);
            }
            if(isGoal(neighbour, currentGoal)){
                visited.put(neighbour, currentCoordinates);
                return node;
            }
        }
        return current;
    }

    private Map<Coordinates, Integer> findNeighbours(Map<Coordinates, Coordinates> visited, Coordinates current, Coordinates currentGoal, GameMap gameMap) {
        Map<Coordinates, Integer> neighbours = new HashMap<>();
        int rowShift = -1, columnShift = -1;
        while (isNotLowerRightCell(rowShift, columnShift)) {
            if (isNewRow(columnShift)) {
                columnShift = -1;
                rowShift++;
            }
            int nearRow = current.row() + rowShift;
            int nearColumn = current.column() + columnShift;
            Coordinates coordinates = new Coordinates(nearRow, nearColumn);
            if (!isValidCell(visited, coordinates, currentGoal, gameMap)) {
                columnShift++;
                continue;
            }

            int weight = ORTHOGONAL_WEIGHT;
            if(isDiagonal(rowShift, columnShift)){
                weight = DIAGONAL_WEIGHT;
            }

            neighbours.put(coordinates, weight);
            columnShift++;
        }
        return neighbours;
    }

    private boolean isValidCell(Map<Coordinates, Coordinates> visited, Coordinates coordinates, Coordinates currentGoal, GameMap gameMap) {
        return gameMap.isValidCoordinate(coordinates)
                && !visited.containsKey(coordinates)
                && (gameMap.isEmpty(coordinates) || isGoal(coordinates, currentGoal));
    }

    private boolean isDiagonal(int rowShift, int columnShift){
        return (columnShift == -1 || columnShift == 1) && (rowShift == -1 || rowShift == 1);
    }

    private boolean isNodeExist(Node node, Queue<Node> forVisit){
        return forVisit.contains(node);
    }

    private boolean canChangeLength(Node node, int newLength, Queue<Node> forVisit){
        return isNodeExist(node, forVisit) && getNodeLength(node, forVisit) > newLength;
    }

    private int getNodeLength(Node node, Queue<Node> forVisit){
        for(Node entry : forVisit){
            if(entry.equals(node)) {
                return entry.pathLength();
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean isGoal(Coordinates coordinates, Coordinates currentGoal) {
        return coordinates.equals(currentGoal);
    }

    private boolean isShorterWay(int lengthToCurrentGoal, int minLengthToGoal){
        return minLengthToGoal == 0 || minLengthToGoal > lengthToCurrentGoal;
    }

}

