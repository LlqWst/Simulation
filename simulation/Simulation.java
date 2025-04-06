package simulation;


import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.entity.staticObjects.Grass;

import java.util.*;

public class Simulation {

    private final Map map = new Map();
    MapRenderer renderer = new MapRenderer(map);

    public void turn (Map map) {

        if(!map.getAllEntities().containsValue(new Grass())){
            map.setEntities(map.getRandomEmptyCoordinates(), new Grass());
            map.setEntities(map.getRandomEmptyCoordinates(), new Grass());
        }

        if(!map.getAllEntities().containsValue(new Herbivore())){
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
        }

        HashMap<Coordinates, Entity> temp = new HashMap<>(map.getAllEntities());
        List<Coordinates> arr = new ArrayList<>();
        for (HashMap.Entry<Coordinates, Entity> entity : temp.entrySet()) {
            if (entity.getValue() instanceof Herbivore) {
                Coordinates coordinates = ((Herbivore) entity.getValue()).makeMove(entity.getKey(), map);
                map.getAllEntities().remove(entity.getKey());
                map.setEntities(coordinates, new Herbivore());
            } else if (entity.getValue() instanceof Predator && !arr.contains(entity.getKey())) {
                Coordinates coordinates = ((Predator) entity.getValue()).makeMove(entity.getKey(), map);
                if(temp.containsKey(coordinates)){
                    temp.put(coordinates, new Predator());
                    arr.add(coordinates);
                }
                map.getAllEntities().remove(entity.getKey());
                map.setEntities(coordinates, new Predator());
            }
        }

    }

    public void game(){
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        do {
            renderer.render();
            n = scanner.nextInt();
            if (n == 1) {
                turn(map);
            }
        } while (n != 3);
    }

}
