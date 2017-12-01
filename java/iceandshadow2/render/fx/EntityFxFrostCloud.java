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
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += velX;
		this.motionY += velY;
		this.motionZ += velZ;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F
				- (float) (Math.random() * 0.30000001192092896D);
		this.particleScale *= size;
		this.particleScale *= f;
		this.field_70569_a = this.particleScale;
		this.particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.3D));
		this.particleMaxAge = (int) (this.particleMaxAge * f);
		this.noClip = false;

	}

	public int getFxLayer() {
		return 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		moveEntity(this.motionX, this.motionY, this.motionZ);

		if (this.particleAge++ >= this.particleMaxAge)
			setDead();

		setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6,
			float par7) {
		float f6 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;

		if (f6 < 0.0F) {
			f6 = 0.0F;
		}

		if (f6 > 1.0F) {
			f6 = 1.0F;
		}

		this.particleScale = this.field_70569_a * f6;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
