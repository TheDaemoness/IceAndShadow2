package iceandshadow2.render;

import net.minecraftforge.client.MinecraftForgeClient;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.mobs.*;
import iceandshadow2.nyx.entities.projectile.*;
import iceandshadow2.render.entity.mobs.*;
import iceandshadow2.render.entity.projectiles.*;
import iceandshadow2.render.item.RenderItemBow;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class IaSRenderers {
	public static void init() {
		
		//Mobs.
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSkeleton.class,
				new RenderNyxSkeleton());

		//Projectiles.
		RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class,
				new RenderIceArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBall.class,
				new RenderNot());
		
		//Items
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowShort, new RenderItemBow(false));
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowLong, new RenderItemBow(true));
	}

}
