package iceandshadow2.render.entity.projectiles;

import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderThrowingKnife extends Render {

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderKnife((EntityThrowingKnife) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if (entity instanceof EntityThrowingKnife)
			return ((EntityThrowingKnife) entity).getTexture();
		return null;
	}

	public void renderKnife(EntityThrowingKnife knife, double par2, double par4, double par6, float par8, float par9) {
		bindTexture(knife.getTexture());
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(knife.prevRotationYaw + (knife.rotationYaw - knife.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F,
				0.0F);
		GL11.glRotatef(knife.prevRotationPitch + (knife.rotationPitch - knife.prevRotationPitch) * par9, 0.0F, 0.0F,
				1.0F);
		final Tessellator var10 = Tessellator.instance;
		final byte var11 = 0;
		final float var12 = 0.0F;
		final float var13 = 0.5F;
		final float var14 = (0 + var11 * 10) / 32.0F;
		final float var15 = (5 + var11 * 10) / 32.0F;
		final float var16 = 0.0F;
		final float var17 = 0.15625F;
		final float var18 = (5 + var11 * 10) / 32.0F;
		final float var19 = (10 + var11 * 10) / 32.0F;
		final float var20 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		final float var21 = knife.arrowShake - par9;

		if (var21 > 0.0F) {
			final float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
		}

		GL11.glRotatef(-knife.ticksExisted * 53.0F, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(var20, var20, var20 / 4);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var19);
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var19);
		var10.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, var16, var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, var17, var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, var17, var19);
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, var16, var19);
		var10.draw();

		for (int var23 = 0; var23 < 4; ++var23) {
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			var10.startDrawingQuads();
			var10.addVertexWithUV(-8.0D, -2.0D, 0.0D, var12, var14);
			var10.addVertexWithUV(8.0D, -2.0D, 0.0D, var13, var14);
			var10.addVertexWithUV(8.0D, 2.0D, 0.0D, var13, var15);
			var10.addVertexWithUV(-8.0D, 2.0D, 0.0D, var12, var15);
			var10.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
