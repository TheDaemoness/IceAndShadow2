package iceandshadow2.render.entity.mobs;

import iceandshadow2.nyx.entities.mobs.EntityNyxWightToxic;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNyxWightToxic extends RenderZombie {

	private static ResourceLocation skin = new ResourceLocation(
			"iceandshadow2:textures/mob/wighttoxic.png");
	private static ResourceLocation skin_glow = new ResourceLocation(
			"iceandshadow2:textures/mob/wighttoxic_glow.png");

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return RenderNyxWightToxic.skin;
    }

	@Override
	public void doRender(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		GL11.glDepthMask(true);
		super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
		func_110827_b(par1EntityLiving, par2, par4, par6, par8, par9);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	protected void setGlow(EntityNyxWightToxic par1Entity,
			int par2, float par3) {
		if (par2 > 1 || par1Entity.isInvisible() || par1Entity.hurtTime > 0
				|| par1Entity.getHealth() <= 0.0F)
			return;
		else {
			final float f1 = 1.0F;

			bindTexture(RenderNyxWightToxic.skin_glow);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);

			final char c0 = 61680;
			final int j = c0 % 65536;
			final int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
			GL11.glScalef(1.01F, 1.01F, 1.01F);
			this.modelBipedMain.bipedBody.renderWithRotation(par3);
			this.modelBipedMain.bipedHead.renderWithRotation(par3);
			this.modelBipedMain.bipedHeadwear.renderWithRotation(par3);
			this.modelBipedMain.bipedLeftArm.renderWithRotation(0.056F);
			this.modelBipedMain.bipedRightArm.renderWithRotation(0.056F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase par1,
			int par2, float par3) {
		setGlow((EntityNyxWightToxic) par1, par2, par3);
		return super.shouldRenderPass(par1, par2, par3);
	}
}
