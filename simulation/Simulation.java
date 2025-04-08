package simulation;


import simulation.entity.Entity;
import simulation.entity.creature.Creature;
import simulation.entity.creature.Herbivore;
import simulation.entity.creature.Predator;
import simulation.entity.staticObjects.Grass;

import java.util.*;

public class Simulation {
    private int turnCounter;
    private final Map map = new Map();
    MapRenderer renderer = new MapRenderer(map);

    public Simulation() {
        this.turnCounter = 0;
    }

    private void addNewEntities(){
        if(!map.getAllEntities().containsValue(new Grass())){
            map.setEntities(map.getRandomEmptyCoordinates(), new Grass());
            map.setEntities(map.getRandomEmptyCoordinates(), new Grass());
            map.setEntities(map.getRandomEmptyCoordinates(), new Grass());
        }

        if(!map.getAllEntities().containsValue(new Herbivore())){
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
            map.setEntities(map.getRandomEmptyCoordinates(), new Herbivore());
        }

    }

    private void nextTurn () {
        HashMap<Coordinates, Entity> temp = new HashMap<>(map.getAllEntities());
        List<Coordinates> arr = new ArrayList<>();
        for (HashMap.Entry<Coordinates, Entity> entity : temp.entrySet()) {
            if(entity.getValue() instanceof Herbivore && !arr.contains(entity.getKey())) {
                int speed = new Herbivore().getSpeed();
                Coordinates tempForDel = entity.getKey();
                List<Coordinates> coordinates = ((Herbivore) entity.getValue()).makeMove(entity.getKey(), map);
                for (int counter = 0, index = 0; counter < speed; counter++, index++) {
                    if(coordinates.size() <= counter || counter == 0){
                        coordinates =  new Herbivore().makeMove(tempForDel, map);
                        index = 0;
                    }
                    map.getAllEntities().remove(tempForDel);
                    map.setEntities(coordinates.get(index), new Herbivore(((Herbivore) entity.getValue()).getHp()));
                    tempForDel = coordinates.get(index);
                    actRenderAddSomeEntities();
                    System.out.println("Herb HP: " + ((Herbivore) entity.getValue()).getHp());
                }
            } else if (entity.getValue() instanceof Predator && !arr.contains(entity.getKey()))  {
                List<Coordinates> coordinates = ((Predator) entity.getValue()).makeMove(entity.getKey(), map);
                Coordinates tempForDel = entity.getKey();
                int speed = new Predator().getSpeed();
                for (int counter = 0, index = 0; counter < speed; counter++, index++) {


                    if(coordinates.size() <= counter && counter != 0){
                        coordinates =  new Predator().makeMove(tempForDel, map);
                        index = 0;
                    }

                    if(coordinates.size() - counter <= new Predator().getRange()){
                        int hp = ((Herbivore) map.getEntities(coordinates.getLast())).getHp() - new Predator().doDamage();
                        if(hp != 0) {
                            map.setEntities(coordinates.getLast(), new Herbivore(hp));
                            //temp.put(coordinates.getLast(), new Herbivore(hp));
                            if(temp.get(coordinates.getLast()) != null && temp.get(coordinates.getLast()) instanceof Herbivore){
                                ((Herbivore) temp.get(coordinates.getLast())).setHp(hp);
                                //temp.replace(coordinates.getLast(), new Herbivore(hp));
                            }
                            ((Herbivore)  map.getEntities(coordinates.getLast())).setHp(hp);
                           // map.getAllEntities().replace(coordinates.getLast(), new Herbivore(hp));
                        } else {
                            map.getAllEntities().remove(coordinates.getLast());
                                //temp.replace(coordinates.getLast(), null);
                                arr.add(coordinates.getLast());
                        }
                        actRenderAddSomeEntities();
                        System.out.println("Herb HPs after damage: " + hp);
                        continue;
                    }

//                    if (temp.containsKey(coordinates.get(index))) {
//                        temp.put(coordinates.get(index), new Predator());
//                        arr.add(coordinates.get(index));
//                    }
                    map.getAllEntities().remove(tempForDel);
                    tempForDel = coordinates.get(index);
                    map.setEntities(coordinates.get(index), new Predator());
                    actRenderAddSomeEntities();
                }
            }
        }

    }

    public void game(){
        Scanner scanner = new Scanner(System.in);
        actRenderAddSomeEntities();
        do {
            turnCounter++;
            System.out.println("Counter: " + turnCounter);
            nextTurn();
        } while (scanner.nextInt() != 3);
        //} while (true);
    }

    private void actRenderAddSomeEntities(){
        addNewEntities();
        renderer.render();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
