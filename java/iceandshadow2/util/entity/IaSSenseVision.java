package iceandshadow2.util.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;

public class IaSSenseVision extends IaSSense {
	private double dist;
	
	public IaSSenseVision(EntityLivingBase elb, double range) {
		super(elb);
		dist = range;
	}
	
	public double getRange(Entity ent) {
		return dist;
	}
	
	public boolean canSense(Entity ent) {
		double rnge = getRange(ent);
		if(owner.getDistanceSqToEntity(ent) > rnge*rnge)
			return false;
		
		double xdif = ent.posX - owner.posX;
    	double zdif = ent.posZ - owner.posZ;
    	
    	if(xdif == 0.0)
    		xdif += 0.0001;
    	
    	double r = Math.sqrt(xdif*xdif + zdif*zdif);
    	double ang = Math.atan(zdif/xdif);
    	
    	ang *= 180.0/Math.PI;
    	
    	if(xdif < 0)
    		ang += 180.0;
    	else if(zdif < 0)
    		ang += 360.0;
    	
    	double delta = ang-owner.rotationYawHead; //NOTE: When the skeleton looks directly at the player, this will be 90.
    	if(delta > 180)
    		return false;
    	
		return owner.canEntityBeSeen(ent);
	}
}
