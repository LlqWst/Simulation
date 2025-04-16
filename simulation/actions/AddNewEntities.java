package simulation.actions;

import simulation.Parameters;
import simulation.entity.Entity;
import simulation.entity.creature.Herbivore;
import simulation.entity.staticObjects.Grass;
import simulation.gameMap.GameMap;

import java.util.List;
import java.util.function.Supplier;

public class AddNewEntities extends Actions{

   private final GameMap gameMap;
   private final int THRESHOLD;
   //private final Parameters parameters;
  // private final Supplier<Entity> supplier;
   private final SpawnCreature spawnCreature;
   private final Class<? extends Entity> clazz;
   Parameters parameters;


    public AddNewEntities(GameMap gameMap, Parameters parameters, SpawnCreature spawnCreature, Class<? extends Entity> clazz) {
        this.gameMap = gameMap;
        this.THRESHOLD = Parameters.THRESHOLD_ENTITY;
        //this.supplier = supplier;
        //this.parameters = parameters;
        this.spawnCreature = spawnCreature;
        this.clazz = clazz;
        this.parameters = parameters;
    }

    private void addNewEntity(){
        int count = gameMap.getCountEntity1(clazz);
        if(count <= THRESHOLD){
            count = parameters.getRandomEntityNumber(clazz);
            for (int i = 0; i < count; i++) {
                spawnCreature.execute();
            }
//            int amount = getSpawnNumber(entity);
//            gameMap.setEntitiesOnRandomCoordinates(entity, amount);
//            System.out.println("were added " + entity.getClass().getName() +" # " + amount);
        }
    }

//    private int getSpawnNumber(Entity entity){
//        if(entity instanceof Grass){
//            return parameters.getRandomGrassNumber();
//        } else if (entity instanceof Herbivore){
//            return parameters.getRandomHerbivoreNumber();
//        }
//        throw new IllegalArgumentException();
//    }

    public void execute() {
        addNewEntity();
    }
}
