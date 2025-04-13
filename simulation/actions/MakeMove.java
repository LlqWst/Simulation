package simulation.actions;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.entity.staticObjects.Grass;

import java.util.HashMap;
import java.util.Map;

public class MakeMove extends Actions{
    private final GameMap gameMap;
    private int id;
    private Coordinates startCoordinates;


    public MakeMove(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void execute () {
        Map<Coordinates, Integer> creatures = new HashMap<>(gameMap.getCreatures());
        for (Map.Entry<Coordinates, Integer> creature : creatures.entrySet()) {
            startCoordinates = creature.getKey();
            id = creature.getValue();
            if(isHerbivoreMove()){
                herbivoreMove();
            } else if(isPredatorMove()){
                predatorMove();
            }
        }
    }

    private boolean isHerbivoreMove(){
        return gameMap.isHerbivore(startCoordinates)
                && gameMap.isContainsId(startCoordinates, id)
                && gameMap.isContainsGrass();
    }

    private boolean isPredatorMove(){
        return gameMap.isPredator(startCoordinates)
                && gameMap.isContainsId(startCoordinates, id)
                && gameMap.isContainsHerbivore();
    }

    private void herbivoreMove(){
        Herbivore herbivore = ((Herbivore) gameMap.getEntities(startCoordinates));
        Coordinates nextMove = herbivore.makeMove(startCoordinates, gameMap);
        printHrbMoves(nextMove);
        gameMap.removeEntities(startCoordinates);
        gameMap.setEntities(nextMove, herbivore);
    }

    private void predatorMove(){
        Predator predator = ((Predator) gameMap.getEntities(startCoordinates));
        Coordinates nextMove = predator.makeMove(startCoordinates, gameMap);
        if(predator.shouldDoDamage(nextMove, gameMap)){
            predatorDoDamage(nextMove, predator);
        } else {
            gameMap.removeEntities(startCoordinates);
            gameMap.setEntities(nextMove, predator);
        }
        printPrdMoves(nextMove);
    }

    private void predatorDoDamage (Coordinates hrbCoordinates, Predator predator){
        Herbivore hrb = ((Herbivore) gameMap.getEntities(hrbCoordinates));
        int hp = hrb.getHp() - predator.doDamage();
        gameMap.removeEntities(hrbCoordinates);
        if (hp > 0) {
            gameMap.setEntities(hrbCoordinates, new Herbivore(hp, hrb.getId()));
        }
    }

    private void printHrbMoves(Coordinates newCoordinates) {
        StringBuilder line = new StringBuilder("hrb id:" + id);
        line.append(", R:").append(startCoordinates.row());
        line.append(", C:").append(startCoordinates.column());
        if (!gameMap.isEmpty(newCoordinates) && gameMap.getEntities(newCoordinates) instanceof Grass) {
            line.append(" Eating");
        }
        line.append(" -> R:").append(newCoordinates.row());
        line.append(", C:").append(newCoordinates.column());
        line.append(", HP: ").append(((Herbivore) gameMap.getEntities(startCoordinates)).getHp());
        System.out.println(line);
    }

    private void printPrdMoves(Coordinates newCoordinates) {
        StringBuilder line = new StringBuilder("prd id:" + id);
        line.append(", R:").append(startCoordinates.row());
        line.append(", C:").append(startCoordinates.column());
        if (gameMap.isEmpty(newCoordinates)) {
            line.append(" Damaging -> hrb is dead on");
        } else if (gameMap.isHerbivore(newCoordinates)) {
            Herbivore hrb = ((Herbivore) gameMap.getEntities(newCoordinates));
            line.append(" Damaging -> hrb id:").append(hrb.getId());
            line.append(" HP:").append(hrb.getHp());
        }
        line.append(" -> R:").append(newCoordinates.row());
        line.append(", C:").append(newCoordinates.column());
        System.out.println(line);
    }

}
