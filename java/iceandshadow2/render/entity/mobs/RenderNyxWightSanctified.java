package iceandshadow2.render.entity.mobs;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNyxWightSanctified extends RenderZombie {

	private static final ResourceLocation wight_body = new ResourceLocation(
			"iceandshadow2:textures/mob/wightsanctified.png");

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return RenderNyxWightSanctified.wight_body;
	}
}
