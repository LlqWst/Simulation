package simulation.actions;

import simulation.Parameters;
import simulation.entity.Entity;
import simulation.gameMap.GameMap;

public class AddNewEntities extends Actions{

   private final GameMap gameMap;
   private final int THRESHOLD;
   private final SpawnCreature spawnCreature;
   private final Class<? extends Entity> clazz;
   Parameters parameters;


    public AddNewEntities(GameMap gameMap, Parameters parameters, SpawnCreature spawnCreature, Class<? extends Entity> clazz) {
        this.gameMap = gameMap;
        this.THRESHOLD = Parameters.THRESHOLD_ENTITY;
        this.spawnCreature = spawnCreature;
        this.clazz = clazz;
        this.parameters = parameters;
    }

    private void addNewEntity(){
        int count = gameMap.getCountEntity(clazz);
        if(count <= THRESHOLD){
            count = parameters.getRandomEntityNumber(clazz);
            for (int i = 0; i < count; i++) {
                spawnCreature.execute();
            }
        }
    }

    @Override
    public void execute() {
        addNewEntity();
    }
}
