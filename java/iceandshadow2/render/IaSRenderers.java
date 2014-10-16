package iceandshadow2.render;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.mobs.*;
import iceandshadow2.nyx.entities.projectile.*;
import iceandshadow2.render.entity.mobs.*;
import iceandshadow2.render.entity.projectiles.*;
import iceandshadow2.render.item.RenderItemBow;
import iceandshadow2.render.item.RenderItemVanillaGlowing;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class IaSRenderers {
	public static void init() {
		
		//Mobs.
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSkeleton.class,
				new RenderNyxSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSpider.class,
				new RenderNyxSpider());

		//Projectiles.
		RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class,
				new RenderIceArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBall.class,
				new RenderNot());
		
		//Items
		for(Field f : NyxItems.class.getFields()) {
			try {
				Object o = f.get(null);
				if(o instanceof IIaSGlowing && o instanceof Item) {
					if(((IIaSGlowing)o).usesDefaultGlowRenderer())
						MinecraftForgeClient.registerItemRenderer((Item)o, new RenderItemVanillaGlowing());
				}
			} catch (Exception e) {}
		}
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowShort, new RenderItemBow(false));
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowLong, new RenderItemBow(true));
		
		for(Item item : IaSTools.tools)
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		for(Item item : IaSTools.toolsActiveEchir)
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
	}

}
