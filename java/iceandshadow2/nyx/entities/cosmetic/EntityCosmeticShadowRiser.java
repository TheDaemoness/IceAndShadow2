package iceandshadow2.nyx.entities.cosmetic;

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

public class EntityCosmeticShadowRiser extends EntityThrowable {

	public boolean isStrong() {
		return (this.getDataWatcher().getWatchableObjectByte(16) & 0x1) != 0;
	}

	public boolean isUndeadHarming() {
		return (this.getDataWatcher().getWatchableObjectByte(16) & 0x2) != 0;
	}

	public EntityCosmeticShadowRiser setFlags(boolean strong, boolean harmUndead) {
		this.getDataWatcher().updateObject(16,
				(byte) ((strong ? 0x1 : 0x0) | (harmUndead ? 0x2 : 0x0)));
		return this;
	}

	private void initFlags(boolean strong, boolean harmUndead) {
		this.getDataWatcher().addObject(16,
				(byte) ((strong ? 0x1 : 0x0) | (harmUndead ? 0x2 : 0x0)));
	}

	public EntityCosmeticShadowRiser(World par1World) {
		super(par1World);
		this.getDataWatcher().addObject(16, (byte) (0));
		this.setThrowableHeading(0, 1, 0, 1.2F, 0F);
		this.setInvisible(true);
	}

	@Override
	protected float func_70182_d() {
		return 0.5F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.001F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if(par1MovingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
			this.setDead();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.ticksExisted > 50 && this.motionY < 0)
			this.setDead();
		String id = "shadowSmokeLarge";
		IaSFxManager.spawnParticle(this.worldObj, id, this.posX, this.posY,
				this.posZ, false, false);
		IaSFxManager.spawnParticle(this.worldObj, id, this.posX + this.motionX,
				this.posY + this.motionY, this.posZ + this.motionZ, false, false);
	}
}
