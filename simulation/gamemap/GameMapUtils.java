package simulation.gamemap;

import simulation.entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

public final class GameMapUtils {

    public static int getCountEntity(GameMap gameMap, Class<? extends Entity> clazz) {
        int counter = 0;
        for (Entity entity : gameMap.getAllEntities()) {
            if (clazz == entity.getClass()) {
                counter++;
            }
        }
        return counter;
    }

    public static boolean isCoordinatesContains(GameMap gameMap, Coordinates coordinates, Class<? extends Entity> clazz) {
        return gameMap.isValidCoordinate(coordinates)
                && !gameMap.isEmpty(coordinates)
                && gameMap.getEntity(coordinates).getClass() == clazz;
    }

    public static Coordinates getRandomEmptyCoordinates(GameMap gameMap) {
        List<Coordinates> allEmptyCoordinates = new ArrayList<>();
        for (int row = 0; row < gameMap.getMaxRow(); row++) {
            for (int column = 0; column < gameMap.getMaxColumn(); column++) {
                Coordinates coordinates = new Coordinates(row, column);
                if (gameMap.isEmpty(coordinates)) {
                    allEmptyCoordinates.add(coordinates);
                }
            }
        }
        Random random = new Random();
        int randomCoordinates = random.nextInt(allEmptyCoordinates.size());
        return allEmptyCoordinates.get(randomCoordinates);
    }

    public static boolean isContains(GameMap gameMap, Class<? extends Entity> clazz) {
        return gameMap.getAllEntities()
                .stream()
                .anyMatch(value ->
                        value.getClass() == clazz);
    }

    public static boolean isOnMap(GameMap gameMap, Entity entity) {
        if (entity == null){
            throw new IllegalArgumentException();
        }
        return gameMap.getAllEntities().contains(entity);
    }

    public static List<Entity> getEntities(GameMap gameMap, Class<? extends Entity> clazz) {
        return gameMap.getAllEntities().stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toList());
    }

}
