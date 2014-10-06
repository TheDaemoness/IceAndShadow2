package iceandshadow2.render.item;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class RenderItemBow extends RenderItemVanilla {
	private boolean lb;

	public RenderItemBow(boolean isLongbow) {
		this.mc = Minecraft.getMinecraft();
		lb = isLongbow;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPopMatrix(); //STAHP PRE-TRANSLATING MY BOW!
		GL11.glPushMatrix();
		 
		float f2 = 3F - (1F/3F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(f2, f2, f2);
		GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
		
		//Standard bow transforms.
		if(!lb) {
			GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.625F, -0.625F, 0.625F);
		}
		else {
			GL11.glTranslatef(0.0F, 0.125F, 0.5F);
			GL11.glScalef(0.625F, -0.5F, 0.875F);
		}
		GL11.glRotatef(-110.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		
		super.renderItem(type, item, data);
		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
	}
}