package iceandshadow2.nyx.entities.projectile;

import iceandshadow2.nyx.items.tools.NyxItemRemote;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.api.IaSGrenadeLogic;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityGrenade extends EntityThrowable implements IIaSAspect {

	protected int fusetime;

	public EntityGrenade(World par1World) {
		super(par1World);
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(17, Integer.MIN_VALUE);
		fusetime = Integer.MIN_VALUE;
	}

	public EntityGrenade(World par1World, double par2, double par4, double par6, int type, int elapsed) {
		super(par1World, par2, par4, par6);
		dataWatcher.addObject(16, type);
		setType(type, elapsed);
	}

	public EntityGrenade(World par1World, EntityLivingBase par2EntityLivingBase, int type, int elapsed) {
		super(par1World, par2EntityLivingBase);
		dataWatcher.addObject(16, type);
		setType(type, elapsed);
	}

	@Override
	public boolean attackEntityFrom(DamageSource ds, float f) {
		if (isDead)
			return false;
		final IaSGrenadeLogic rapper = getLogic();
		if (!ds.isMagicDamage()) {
			setDead();
			if (ds.isExplosion() || fusetime > 0)
				rapper.onDetonate(this);
			return true;
		}
		return false;
	}

	@Override
	protected float func_70182_d() {
		return 1F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NATIVE;
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	public IaSGrenadeLogic getLogic() {
		return IaSRegistry.getGrenadeLogic(dataWatcher.getWatchableObjectInt(16));
	}

	/**
	 * Called when this EntityThrowable hits a block or entity. I swear, there needs
	 * to be an elasticity value for MC blocks. Creating a library mod that IaS2
	 * depends on seems better and better.
	 */
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			final ForgeDirection dir = ForgeDirection.getOrientation(mop.sideHit);
			final Block bl = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			final float slip = (bl != null) ? bl.slipperiness : Blocks.stone.slipperiness; // I know, but just in case.
			final float realslip = Math.min(0.99f, slip * slip);
			motionX = (dir.offsetX == 0 ? motionX : Math.abs(motionX) * dir.offsetX) * realslip;
			motionY = (dir.offsetY == 0 ? motionY : Math.abs(motionY) * dir.offsetY) / 4;
			motionZ = (dir.offsetZ == 0 ? motionZ : Math.abs(motionZ) * dir.offsetZ) * realslip;
			// TODO: Rewrite this minefield into a pair of functions in IaSBlockHelper.
			final double xW = (bl.getBlockBoundsMaxX() - bl.getBlockBoundsMinX()) / 2,
					yW = (bl.getBlockBoundsMaxY() - bl.getBlockBoundsMinY()) / 2,
					zW = (bl.getBlockBoundsMaxZ() - bl.getBlockBoundsMinZ()) / 2,
					xM = (bl.getBlockBoundsMaxX() + bl.getBlockBoundsMinX()) / 2,
					yM = (bl.getBlockBoundsMaxY() + bl.getBlockBoundsMinY()) / 2,
					zM = (bl.getBlockBoundsMaxZ() + bl.getBlockBoundsMinZ()) / 2;
			final double xD = xM + xW * dir.offsetX * 1.001, yD = yM + yW * dir.offsetY * 1.001,
					zD = zM + zW * dir.offsetZ * 1.001;
			if (dir.offsetX != 0)
				posX = mop.blockX + xD;
			if (dir.offsetY != 0)
				posY = mop.blockY + yD;
			if (dir.offsetZ != 0)
				posZ = mop.blockZ + zD;
		} else if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
			motionX = motionY = motionZ = 0;
			mop.entityHit.applyEntityCollision(this);
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		final IaSGrenadeLogic al = getLogic();

		if (dataWatcher.getWatchableObjectInt(17) > fusetime)
			fusetime = dataWatcher.getWatchableObjectInt(17);

		if (fusetime < 0 && getThrower() != null) {
			final ItemStack is = getThrower().getHeldItem();
			if (is != null && is.getItem() == NyxItems.remoteDetonator && NyxItemRemote.isPressed(is))
				fusetime = 0;
		}

		if (fusetime == 0) {
			if (!worldObj.isRemote)
				dataWatcher.updateObject(17,
						getLogic().fuseLimit / 2 + worldObj.rand.nextInt(getLogic().fuseLimit / 4));
			getLogic().playFuseSound(this);
		}

		if (fusetime > getLogic().fuseLimit) {
			setDead();
			al.onDetonate(this);
			return;
		} else {
			++fusetime;
			if (fusetime > 0 && worldObj.isRemote)
				al.onSpawnParticle(worldObj, posX, posY, posZ);
		}

	}

	public void setType(int type, int elapsed) {
		dataWatcher.updateObject(16, type);
		IaSRegistry.getGrenadeLogic(type);
		if (elapsed == 0)
			elapsed = (int) -(getLogic().fuseLimit * (15 + worldObj.rand.nextFloat() * 10));
		dataWatcher.addObject(17, elapsed);
		fusetime = elapsed;
	}

}
