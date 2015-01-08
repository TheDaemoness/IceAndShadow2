package iceandshadow2.render;

import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import iceandshadow2.render.entity.mobs.RenderNyxSkeleton;
import iceandshadow2.render.entity.mobs.RenderNyxSpider;
import iceandshadow2.render.entity.projectiles.RenderIceArrow;
import iceandshadow2.render.entity.projectiles.RenderNot;
import iceandshadow2.render.entity.projectiles.RenderThrowingKnife;
import iceandshadow2.render.item.RenderItemBow;
import iceandshadow2.render.item.RenderItemVanillaGlowing;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowingKnife.class,
				new RenderThrowingKnife());
		
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
		for(Item item : IaSTools.weapons)
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		for(Item item : IaSTools.swordsActiveEchir)
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		for(Item item : IaSTools.armorActiveEchir)
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
	}

}
