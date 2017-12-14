package iceandshadow2.render.fx;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/*
 * This was mostly liberally copied and pasted from the EntityDropParticle effect.
 */
public class EntityFxPoisonDroplet extends EntityFX {

	/** the material type for dropped items/blocks */
	private final Material materialType;

	/** The height of the current bob */
	private int bobTimer;

	public EntityFxPoisonDroplet(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);

		particleRed = 0.0F;
		particleGreen = 0.5F;
		particleBlue = 0.1F;

		setParticleTextureIndex(113);
		setSize(0.01F, 0.01F);
		particleGravity = 0.06F;
		materialType = Material.water;
		bobTimer = 40;
		particleMaxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
		motionX = motionY = motionZ = 0.0D;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		motionY -= particleGravity;

		if (bobTimer-- > 0) {
			motionX *= 0.02D;
			motionY *= 0.02D;
			motionZ *= 0.02D;
			setParticleTextureIndex(113);
		} else
			setParticleTextureIndex(112);

		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if (particleMaxAge-- <= 0)
			setDead();

		if (isCollidedVertically && onGround)
			setDead();
		// this.worldObj.spawnParticle("splash", this.posX, this.posY,
		// this.posZ, 0.0D, 0.0D, 0.0D);

		final Material material = worldObj
				.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))
				.getMaterial();

		if (material.isLiquid() || material.isSolid()) {
			final double d0 = MathHelper.floor_double(posY) + 1
					- BlockLiquid.getLiquidHeightPercent(worldObj.getBlockMetadata(MathHelper.floor_double(posX),
							MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));

			if (posY < d0)
				setDead();
		}
	}
}
