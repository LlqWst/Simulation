package simulation.actions;

import simulation.Parameters;
import simulation.gameMap.GameMap;
import simulation.entity.creature.*;
import simulation.entity.staticObjects.*;

public class InitActions extends Actions{
    private final GameMap gameMap;

    public InitActions(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private void setEntities(){
        for (int i = 0; i < parameters.getPredatorNumber(); i++){
            gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Predator());
        }
        for (int i = 0; i < parameters.getHerbivoreNumber(); i++){
            gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Herbivore());
        }
        for (int i = 0; i < parameters.getGrassNumber(); i++){
            gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Grass());
        }
        for (int i = 0; i < parameters.getTreeNumber(); i++){
            gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Tree());
        }
        for (int i = 0; i < parameters.getRockNumber(); i++){
            gameMap.setEntities(gameMap.getRandomEmptyCoordinates(), new Rock());
        }
    }

    public void execute() {
        setEntities();
    }
}
