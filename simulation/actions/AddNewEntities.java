package simulation.actions;

import simulation.Parameters;
import simulation.entity.Entity;
import simulation.gameMap.GameMap;

public class AddNewEntities extends Actions{

   private final GameMap gameMap;
   private final int THRESHOLD;
   private final SpawnEntity spawnEntity;
   private final Class<? extends Entity> clazz;
   Parameters parameters;


    public AddNewEntities(GameMap gameMap, Parameters parameters, SpawnEntity spawnEntity, Class<? extends Entity> clazz) {
        this.gameMap = gameMap;
        this.THRESHOLD = Parameters.THRESHOLD_ENTITY;
        this.spawnEntity = spawnEntity;
        this.clazz = clazz;
        this.parameters = parameters;
    }

    private void addNewEntity(){
        int count = gameMap.getCountEntity(clazz);
        if(count <= THRESHOLD){
            count = parameters.getRandomEntityNumber(clazz);
            for (int i = 0; i < count; i++) {
                spawnEntity.execute();
            }
        }
    }

    @Override
    public void execute() {
        addNewEntity();
    }
}
