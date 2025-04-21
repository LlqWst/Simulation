package simulation.handler;

import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.gamemap.Coordinates;
import simulation.gamemap.GameMap;

public class PrintMoves {

    private final GameMap gameMap;

    public PrintMoves(GameMap gameMap) {
        this.gameMap = gameMap;
    }


    public void printAll(Creature creature, Coordinates startCoordinates, Coordinates newCoordinates) {
        if (creature instanceof Herbivore herbivore) {
            print(herbivore, startCoordinates, newCoordinates);
        } else if (creature instanceof Predator predator) {
            print(predator, startCoordinates, newCoordinates);
        }
    }

    public void print(Herbivore herbivore, Coordinates startCoordinates, Coordinates newCoordinates) {
        int hp = herbivore.getHp();
        StringBuilder line = new StringBuilder(String.format("hrb (R:%d, C:%d)", startCoordinates.row() + 1, startCoordinates.column() + 1));

        if (hasGetGoal(newCoordinates, herbivore.getGoal())) {
            line.append(" Eating");
        }
        line.append(String.format(" -> (R:%d, C:%d), HP:%d", newCoordinates.row() + 1, newCoordinates.column() + 1, hp));
        System.out.println(line);
    }

    private boolean hasGetGoal(Coordinates coordinates, Class<? extends Entity> clazz) {
        return gameMap.isCoordinatesContains(coordinates, clazz);
    }

    public void print(Predator predator, Coordinates startCoordinates, Coordinates newCoordinates) {
        StringBuilder line = new StringBuilder((String.format("prd (R:%d, C:%d), dmg:%d", startCoordinates.row() + 1, startCoordinates.column() + 1, predator.getDamage())));
        if (isDead(newCoordinates)) {
            line.append(" Damaging, hrb is dead on");
        } else if (hasGetGoal(newCoordinates, predator.getGoal())) {
            Herbivore hrb = ((Herbivore) gameMap.getEntity(newCoordinates));
            int hrbHp = hrb.getHp();
            line.append(String.format(" Damaging hrb, HP:%d", hrbHp));
        }
        line.append(String.format(" -> (R:%d, C:%d)", newCoordinates.row() + 1, newCoordinates.column() + 1));
        System.out.println(line);
    }

    private boolean isDead(Coordinates coordinates) {
        return gameMap.isEmpty(coordinates);
    }

}
