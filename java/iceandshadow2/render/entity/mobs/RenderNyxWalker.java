package iceandshadow2.render.entity.mobs;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNyxWalker extends RenderZombie {

	private static ResourceLocation skin = new ResourceLocation("iceandshadow2:textures/mob/walker.png");

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return skin;
	}
}
