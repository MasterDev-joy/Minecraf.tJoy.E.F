package joymaster.epicfight.capabilities.entity.mob;

import joymaster.epicfight.gamedata.Models;
import joymaster.epicfight.model.Model;
import net.minecraft.entity.monster.EntityZombieVillager;

public class ZombieVillagerData extends ZombieData<EntityZombieVillager> {
	@Override
	public <M extends Model> M getEntityModel(Models<M> modelDB) {
		return modelDB.ENTITY_VILLAGER_ZOMBIE;
	}
}