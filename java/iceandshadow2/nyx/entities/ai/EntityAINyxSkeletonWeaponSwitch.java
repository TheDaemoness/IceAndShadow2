package iceandshadow2.nyx.entities.ai;

import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.Potion;

public class EntityAINyxSkeletonWeaponSwitch extends EntityAIBase {

    /** The winter skeleton. */
    EntityNyxSkeleton skel;

    public EntityAINyxSkeletonWeaponSwitch(EntityNyxSkeleton skello)
    {
        this.skel = skello;
        this.setMutexBits(1);
    }
    
	@Override
	public boolean shouldExecute() {
        EntityLivingBase entityliving = this.skel.getAttackTarget();
        if(entityliving != null) {
        	if(this.skel.getHeldItem() == null && this.skel.getReserveWeapon() != null)
        		return true;
        	if(skel.getNyxSkeletonCombatType() == EntityNyxSkeleton.EnumNyxSkeletonType.BOW_FROST_SHORT) {
        		if(skel.isUsingAlternateWeapon()) {
        			if(skel.isPotionActive(Potion.moveSlowdown.id) && skel.getDistanceSqToEntity(entityliving) > 9)
        				return true;
        			if(skel.getDistanceSqToEntity(entityliving) > 25)
        				return true;
        		}
        		else {
            		if(skel.isPotionActive(Potion.moveSlowdown.id) && skel.getDistanceSqToEntity(entityliving) < 4)
            			return true;
        			if(!skel.isPotionActive(Potion.moveSlowdown.id) && skel.getDistanceSqToEntity(entityliving) < 16)
        				return true;
        		}
        	}
        }
        return false;
	}
	
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        EntityLivingBase entityliving = this.skel.getAttackTarget();
        if(entityliving != null) {
        	if(skel.getNyxSkeletonCombatType() == EntityNyxSkeleton.EnumNyxSkeletonType.BOW_FROST_SHORT) {
        		if(skel.isUsingAlternateWeapon())
        			skel.useAlternateWeapon(false);
        		else
        			skel.useAlternateWeapon(true);
        	}
        	else if(this.skel.getHeldItem() == null) {
        		if(skel.isUsingAlternateWeapon())
        			skel.useAlternateWeapon(false);
        		else
        			skel.useAlternateWeapon(true);
        	}
        }
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
    }
}
