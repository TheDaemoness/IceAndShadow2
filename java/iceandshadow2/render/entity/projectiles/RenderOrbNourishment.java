package iceandshadow2.render.entity.projectiles;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.render.IaSRenderHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class RenderOrbNourishment extends RenderXPOrb {

	@Override
	public void doRender(EntityXPOrb orb, double x, double y, double z, float a, float b) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		bindEntityTexture(orb);
		final int iconIndex = orb.getTextureByXP();
		final float
		minU = (iconIndex % 4 * 16 + 0) / 64f,
		maxU = (iconIndex % 4 * 16 + 16) / 64f,
		minV = (iconIndex / 4 * 16 + 0) / 64f,
		maxV = (iconIndex / 4 * 16 + 16) / 64f;
		final int
		luma = orb.getBrightnessForRender(b),
		lumaLSB = luma & 65535,
		lumaMSB = luma / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lumaLSB, lumaMSB);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		final int color = 255 << 16 | 0 << 8 | (int) (((MathHelper.sin((orb.xpColor + b) / 2.0F) + 1) / 2) * 64); // Color.
		IaSRenderHelper.rotateTowardPlayer(renderManager);
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(color, 128);
		IaSRenderHelper.addUVSquare(tessellator, minU, maxU, minV, maxV, 0.3f);
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
