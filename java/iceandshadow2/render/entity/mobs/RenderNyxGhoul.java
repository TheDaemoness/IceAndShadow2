package iceandshadow2.render.entity.mobs;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNyxGhoul extends RenderZombie {
	
	private static final ResourceLocation ghoul_body = new 
			ResourceLocation("iceandshadow2:textures/mob/whiteghoul.png");
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ghoul_body;
    }
}
