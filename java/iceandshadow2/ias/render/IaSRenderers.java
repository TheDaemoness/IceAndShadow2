package iceandshadow2.ias.render;

import net.minecraftforge.client.MinecraftForgeClient;
import iceandshadow2.ias.render.entity.*;
import iceandshadow2.ias.render.item.RenderItemBow;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.*;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class IaSRenderers {
	public static void init() {

		//Projectiles.
		RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class,
				new RenderIceArrow());
		
		//Items
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowShort, new RenderItemBow(false));
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowLong, new RenderItemBow(true));
	}

}
