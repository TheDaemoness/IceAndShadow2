package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.entities.mobs.EntityNyxNecromancer;
import iceandshadow2.render.fx.IaSFxManager;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityPoisonBall extends EntityThrowable {

	public EntityPoisonBall(World par1World) {
		super(par1World);
	}

	@SideOnly(Side.CLIENT)
	public EntityPoisonBall(World par1World, double par2, double par4,
			double par6) {
		super(par1World, par2, par4, par6);
	}

	public EntityPoisonBall(World par1World,
			EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return 0.5F;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.02F;
	}
	
	

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {

		if (par1MovingObjectPosition.typeOfHit == MovingObjectType.ENTITY) {
			if (this.worldObj.isRemote) {
				this.setDead();
				return;
			}
			if (par1MovingObjectPosition.entityHit instanceof EntityLivingBase) {
				EntityLivingBase victim = (EntityLivingBase)(par1MovingObjectPosition.entityHit);
			}
		
		}
		this.setDead();
	}
}
