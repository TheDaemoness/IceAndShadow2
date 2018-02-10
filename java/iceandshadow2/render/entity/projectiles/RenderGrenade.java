package iceandshadow2.render.entity.projectiles;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import iceandshadow2.nyx.items.tools.NyxItemGrenade;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderGrenade extends Render {

	@Override
	public void doRender(Entity e, double x, double y, double z,
			float a, float b) {
		doRender((EntityGrenade)e, (float)x, (float)y, (float)z);
	}

	public void doRender(EntityGrenade e, float x, float y, float z) {
		GL11.glPushMatrix();
		this.bindEntityTexture(e);
		GL11.glTranslatef(x, y, z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glRotatef(180f - renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(-renderManager.playerViewX, 1, 0, 0);
		final IIcon icon = ((NyxItemGrenade)NyxItems.grenade).getPayloadIcon(e.getLogic().getId());
		final Tessellator t = Tessellator.instance;
		final float
		minU = icon.getMinU(),
		maxU = icon.getMaxU(),
		minV = icon.getMinV(),
		maxV = icon.getMaxV();
		t.startDrawingQuads();
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.25, 0, (double)minU, (double)maxV);
		t.addVertexWithUV(0.5, -0.25, 0, (double)maxU, (double)maxV);
		t.addVertexWithUV(0.5, 0.75, 0, (double)maxU, (double)minV);
		t.addVertexWithUV(-0.5, 0.75, 0, (double)minU, (double)minV);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		t.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return TextureMap.locationItemsTexture;
	}


}
