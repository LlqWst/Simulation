package simulation.entity.actions;

import simulation.Coordinates;
import simulation.GameMap;
import simulation.entity.creature.*;
import simulation.entity.staticObjects.*;

public class InitActions extends Actions{
    private final GameMap gameMap;

    public InitActions(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    private void setEntities (){
        gameMap.setEntities(new Coordinates(4, 5), new Herbivore());
        gameMap.setEntities(new Coordinates(4, 7), new Predator());
        gameMap.setEntities(new Coordinates(6, 0), new Herbivore());
        gameMap.setEntities(new Coordinates(4, 6), new Grass());
        //gameMap.setEntities(new Coordinates(8, 8), new Grass());
        //gameMap.setEntities(new Coordinates(5, 8), new Grass());
        gameMap.setEntities(new Coordinates(5, 4), new Grass());
        gameMap.setEntities(new Coordinates(5, 3), new Grass());
        //gameMap.setEntities(new Coordinates(6, 8), new Grass());
        gameMap.setEntities(new Coordinates(4, 0), new Grass());
        gameMap.setEntities(new Coordinates(2, 7), new Herbivore());
        gameMap.setEntities(new Coordinates(2, 2), new Tree());
        gameMap.setEntities(new Coordinates(0, 2), new Tree());
        gameMap.setEntities(new Coordinates(1, 3), new Tree());
        gameMap.setEntities(new Coordinates(0, 3), new Tree());
        //gameMap.setEntities(new Coordinates(3, 9), new Rock());
        //gameMap.setEntities(new Coordinates(7, 8), new Herbivore());
        gameMap.setEntities(new Coordinates(1, 5), new Tree());
        gameMap.setEntities(new Coordinates(1, 5), new Tree());
        gameMap.setEntities(new Coordinates(4, 4), new Tree());
        gameMap.setEntities(new Coordinates(2, 3), new Tree());
        gameMap.setEntities(new Coordinates(6, 5), new Tree());
        gameMap.setEntities(new Coordinates(7, 5), new Tree());
        gameMap.setEntities(new Coordinates(7, 4), new Rock());
        //gameMap.setEntities(new Coordinates(0, 9), new Grass());
        gameMap.setEntities(new Coordinates(0, 0), new Grass());
        gameMap.setEntities(new Coordinates(3, 4), new Rock());
        gameMap.setEntities(new Coordinates(5, 6), new Rock());
        gameMap.setEntities(new Coordinates(3, 0), new Rock());
        gameMap.setEntities(new Coordinates(0, 2), new Predator());
    }

    public void execute() {
        setEntities();
    }
}
