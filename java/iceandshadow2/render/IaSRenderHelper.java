package iceandshadow2.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public class IaSRenderHelper {
	
	public static void rotateTowardPlayer(RenderManager r) {
		GL11.glRotatef(180f - r.playerViewY, 0, 1, 0);
		GL11.glRotatef(-r.playerViewX, 1, 0, 0);
	}
	
	public static void addUVSquare(Tessellator t, double minU, double maxU, double minV, double maxV, float scale) {
		t.setNormal(0, 1, 0);
		t.addVertexWithUV(-0.5, -0.25, 0, minU, maxV);
		t.addVertexWithUV(0.5, -0.25, 0, maxU, maxV);
		t.addVertexWithUV(0.5, 0.75, 0, maxU, minV);
		t.addVertexWithUV(-0.5, 0.75, 0, minU, minV);
		GL11.glScalef(scale, scale, scale);
	}

	public static void addUVSquare(Tessellator t, IIcon icon, float scale) {
		addUVSquare(t, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), scale);
	}
}
