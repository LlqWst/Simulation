package simulation.actions;

import simulation.Parameters;
import simulation.gameMap.GameMap;
import simulation.entity.creature.Herbivore;
import simulation.entity.staticObjects.Grass;

public class AddNewEntities extends Actions{

    private static final int MIN_ENTITY = 2;

    GameMap gameMap;

    public AddNewEntities(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private void addNewEntities(){
        if(gameMap.getCountGrass() <= MIN_ENTITY){
            for (int i = 0; i < parameters.getGrassNumber(); i++) {
                gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Grass());
            }
        }

        if(gameMap.getCountHerbivore() <= MIN_ENTITY){
            for (int i = 0; i < parameters.getHerbivoreNumber(); i++) {
                gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Herbivore());
            }
        }
    }

    public void execute() {
        addNewEntities();
    }
}
