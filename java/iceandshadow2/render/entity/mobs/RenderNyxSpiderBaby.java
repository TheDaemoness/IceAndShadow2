package iceandshadow2.render.entity.mobs;

import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNyxSpiderBaby extends RenderNyxSpider {

	public RenderNyxSpiderBaby() {
		super();
		shadowSize *= 0.5;
	}

	protected void scaleSpider(EntityNyxSpider par1EntityCaveSpider, float par2) {
		GL11.glScalef(0.3F, 0.2F, 0.3F);
	}
}
