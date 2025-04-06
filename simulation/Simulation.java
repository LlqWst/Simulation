package simulation;


import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Simulation {

    private final Map map = new Map();
    MapRenderer renderer = new MapRenderer(map);

    public void turn (Map map) {
//        HashMap<Coordinates, Entity> temp = new HashMap<>();
//        for (HashMap.Entry<Coordinates, Entity> entity : map.getAllEntities().entrySet()) {
//            if (entity.getValue() instanceof Herbivore) {
//                Coordinates coordinates = ((Herbivore) entity.getValue()).makeMove(entity.getKey(), map);
//                temp.put(coordinates, new Herbivore());
//            }
//            else if (!temp.containsKey(entity.getKey())){
//                temp.put(entity.getKey(), entity.getValue());
//            }
//        }
//        map.getAllEntities().clear();
//        map.getAllEntities().putAll(temp);

        HashMap<Coordinates, Entity> temp = new HashMap<>(map.getAllEntities());
        Iterator<HashMap.Entry<Coordinates, Entity>> iterator = temp.entrySet().iterator();
        while (iterator.hasNext()){
            HashMap.Entry<Coordinates, Entity> entity = iterator.next();
            if(entity.getValue() instanceof Herbivore){
                Coordinates coordinates = ((Herbivore) entity.getValue()).makeMove(entity.getKey(), map);
                map.getAllEntities().remove(entity.getKey());
                map.setEntities(coordinates, new Herbivore());
                //((Herbivore) map.getEntities(coordinates)).changeStatus();
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
