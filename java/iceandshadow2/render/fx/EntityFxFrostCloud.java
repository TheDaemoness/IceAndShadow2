package iceandshadow2.render.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityFxFrostCloud extends EntityFX {

	protected float field_70569_a;

	public EntityFxFrostCloud(World par1World, double x, double y, double z, double velX, double velY, double velZ,
			float size) {
		super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
		final float f = 2.5F;
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += velX;
		motionY += velY;
		motionZ += velZ;
		particleRed = particleGreen = particleBlue = 1.0F
				- (float) (Math.random() * 0.30000001192092896D);
		particleScale *= size;
		particleScale *= f;
		field_70569_a = particleScale;
		particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.3D));
		particleMaxAge = (int) (particleMaxAge * f);
		noClip = false;

	}

	public int getFxLayer() {
		return 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		moveEntity(motionX, motionY, motionZ);

		if (particleAge++ >= particleMaxAge)
			setDead();

		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);

		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6,
			float par7) {
		float f6 = (particleAge + par2) / particleMaxAge * 32.0F;

		if (f6 < 0.0F)
			f6 = 0.0F;

		if (f6 > 1.0F)
			f6 = 1.0F;

		particleScale = field_70569_a * f6;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
