package simulation;


import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;

import java.util.HashMap;

public class Simulation {

    private final Map map = new Map();
    MapRenderer renderer = new MapRenderer(map);

    public void turn (Map map) {
        HashMap<Coordinates, Entity> temp = new HashMap<>();
        for (HashMap.Entry<Coordinates, Entity> entity : map.getAllEntities().entrySet()) {
            if (entity.getValue() instanceof Herbivore) {
                Coordinates coordinates = ((Herbivore) entity.getValue()).makeMove(entity.getKey(), map);
                temp.put(coordinates, new Herbivore());
            }
            else {
                temp.put(entity.getKey(), entity.getValue());
            }
        }
        map.getAllEntities().clear();
        map.getAllEntities().putAll(temp);
    }

    public void game(){
        renderer.render();
        turn(map);
        renderer.render();
    }

}
