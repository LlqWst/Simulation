package simulation;

import simulation.entity.Entity;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.gameMap.Coordinates;
import simulation.gameMap.GameMap;

public class PrintMoves {

    private final GameMap gameMap;

    public PrintMoves(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void print(Herbivore herbivore, Coordinates startCoordinates, Coordinates newCoordinates){
        int id = herbivore.getId();
        int hp = herbivore.getHp();
        StringBuilder line = new StringBuilder(String.format("hrb id:%d", id));
        line.append(String.format(", (R:%d, C:%d)", startCoordinates.row()+1, startCoordinates.column()+1));
        if (hasGetGoal(newCoordinates, herbivore.getGoal())) {
            line.append(" Eating");
        }
        line.append(String.format(" -> (R:%d, C:%d), HP:%d", newCoordinates.row()+1, newCoordinates.column()+1, hp));
        System.out.println(line);
    }

    private boolean hasGetGoal(Coordinates coordinates, Class<? extends Entity> clazz){
        return gameMap.isCoordinatesContain(coordinates, clazz);
    }

    public void print(Predator predator, Coordinates startCoordinates, Coordinates newCoordinates){
        int prdId = predator.getId();
        StringBuilder line = new StringBuilder(String.format("prd id:%d", prdId));
        line.append(String.format(", (R:%d, C:%d)", startCoordinates.row()+1, startCoordinates.column()+1));
        if (isDead(newCoordinates)) {
            line.append(" Damaging, hrb is dead on");
        } else if (hasGetGoal(newCoordinates, predator.getGoal())) {
            Herbivore hrb = ((Herbivore) gameMap.getEntity(newCoordinates));
            int hrbId = hrb.getId();
            int hrbHp = hrb.getHp();
            line.append(String.format(" Damaging -> hrb id:%d, HP:%d", hrbId, hrbHp));
        }
        line.append(String.format(" -> (R:%d, C:%d)", newCoordinates.row()+1, newCoordinates.column()+1));
        System.out.println(line);
    }

    private boolean isDead(Coordinates coordinates){
        return gameMap.isEmpty(coordinates);
    }

}
