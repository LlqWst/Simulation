package simulation.entity.actions;

import simulation.GameMap;
import simulation.entity.creature.Herbivore;
import simulation.entity.staticObjects.Grass;

public class AddNewEntities extends Actions{

    GameMap gameMap;

    public AddNewEntities(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private void addNewEntities(){
        if(!gameMap.isContainsGrass()){
            for (int i = 0; i < 3; i++) {
                gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Grass());
            }
        }

        if(!gameMap.isContainsHerbivore()){
            for (int i = 0; i < 3; i++) {
                gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Herbivore());
            }
        }
    }

    public void execute() {
        addNewEntities();
    }
}
