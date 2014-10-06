package iceandshadow2.nyx.entities.ai.senses;

import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseVisionLight extends IaSSenseVision {
	public IaSSenseVisionLight(EntityLivingBase elb, double range) {
		super(elb, range);
	}
	
	public double getRange(Entity ent) {
		return super.getRange()*15.0/IaSEntityHelper.getLight(ent);
	}
	
	public boolean canSense(Entity ent) {
		if(IaSEntityHelper.getLight(ent) < 7)
			return false;
		if(!super.canSense(ent))
			return false;
		return true;
		
	}
}
