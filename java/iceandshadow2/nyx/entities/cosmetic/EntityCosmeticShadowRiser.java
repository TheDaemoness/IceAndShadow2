package iceandshadow2.nyx.entities.cosmetic;

import iceandshadow2.render.fx.IaSFxManager;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCosmeticShadowRiser extends EntityThrowable {

	public EntityCosmeticShadowRiser(World par1World) {
		super(par1World);
		getDataWatcher().addObject(16, (byte) (0));
		setThrowableHeading(0, 1, 0, 1.2F, 0F);
		setInvisible(true);
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

	private void initFlags(boolean strong, boolean harmUndead) {
		getDataWatcher().addObject(16, (byte) ((strong ? 0x1 : 0x0) | (harmUndead ? 0x2 : 0x0)));
	}

	public boolean isStrong() {
		return (getDataWatcher().getWatchableObjectByte(16) & 0x1) != 0;
	}

	public boolean isUndeadHarming() {
		return (getDataWatcher().getWatchableObjectByte(16) & 0x2) != 0;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition) {
		if (par1MovingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
			setDead();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.ticksExisted > 50 && this.motionY < 0)
			setDead();
		final String id = "shadowSmokeLarge";
		IaSFxManager.spawnParticle(this.worldObj, id, this.posX, this.posY, this.posZ, false, false);
		IaSFxManager.spawnParticle(this.worldObj, id, this.posX + this.motionX, this.posY + this.motionY,
				this.posZ + this.motionZ, false, false);
	}

	public EntityCosmeticShadowRiser setFlags(boolean strong, boolean harmUndead) {
		getDataWatcher().updateObject(16, (byte) ((strong ? 0x1 : 0x0) | (harmUndead ? 0x2 : 0x0)));
		return this;
	}
}
