package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.nyx.entities.mobs.EntityNyxNecromancer;
import iceandshadow2.render.fx.IaSFxManager;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGrenade extends EntityThrowable implements IIaSAspect {

	protected int fusetime;
	
	public EntityGrenade(World par1World) {
		super(par1World);
		this.dataWatcher.addObject(16, 0);
		this.dataWatcher.addObject(17, 0);
		setType(0);
	}

	public EntityGrenade(World par1World, double par2, double par4, double par6, int type, int elapsed) {
		super(par1World, par2, par4, par6);
		this.dataWatcher.addObject(16, type);
		this.dataWatcher.addObject(17, elapsed);
		setType(type);
	}
	
	public EntityGrenade(World par1World, EntityLivingBase par2EntityLivingBase, int type, int elapsed) {
		super(par1World, par2EntityLivingBase);
		this.dataWatcher.addObject(16, type);
		this.dataWatcher.addObject(17, elapsed);
		setType(type);
	}

	@Override
	protected float func_70182_d() {
		return 0.75F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}
	
	public IaSGrenadeLogic getLogic() {
		return IaSRegistry.getGrenadeLogic(this.dataWatcher.getWatchableObjectInt(16));
	}

	public void setType(int type) {
		this.dataWatcher.updateObject(16, type);
		final IaSGrenadeLogic gate = IaSRegistry.getGrenadeLogic(type);
		this.fusetime = gate.fuseOnImpact?0:1;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 * I swear, there needs to be an elasticity value for MC blocks.
	 * Creating a library mod that IaS2 depends on seems better and better.
	 */
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			final ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			this.motionX = (dir.offsetX==0?this.motionX:Math.abs(this.motionX)*dir.offsetX)/3;
			this.motionY = (dir.offsetY==0?this.motionY:Math.abs(this.motionY)*dir.offsetY)/4;
			this.motionZ = (dir.offsetZ==0?this.motionZ:Math.abs(this.motionZ)*dir.offsetZ)/3;
		} else if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			motionX = motionY = motionZ = 0;
			mop.entityHit.applyEntityCollision(this);
		}
		if(fusetime == 0)
			fusetime = 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		final IaSGrenadeLogic al=getLogic();
		
		if(dataWatcher.getWatchableObjectInt(17) > fusetime)
			fusetime = dataWatcher.getWatchableObjectInt(17);
		
		if(fusetime > getLogic().fuseLimit) {
			al.onDetonate(this);
			this.setDead();
			return;
		} else if(fusetime > 0)
			++fusetime;
		
		if(worldObj.isRemote)
			al.onSpawnParticle(worldObj, posX, posY, posZ, fusetime > 0);
	}
}
