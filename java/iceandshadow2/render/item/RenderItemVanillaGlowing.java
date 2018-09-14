package iceandshadow2.render.item;

import iceandshadow2.ias.api.IIaSTool;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import scala.Char;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItemVanillaGlowing implements IItemRenderer {

	private final RenderItem rend;
	protected Minecraft mc;

	public RenderItemVanillaGlowing() {
		rend = new RenderItem();
		mc = Minecraft.getMinecraft();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED
				|| (type == ItemRenderType.ENTITY && Minecraft.isFancyGraphicsEnabled());
	}

	public void renderItem(Entity entity, ItemStack is, int pass, ItemRenderer rendr, boolean doGlowTransforms,
			ItemRenderType type) {

		GL11.glPushMatrix();

		IIcon icon;
		if (entity instanceof EntityLivingBase)
			icon = ((EntityLivingBase) entity).getItemIcon(is, pass);
		else
			icon = is.getItem().getIcon(is, pass);

		if (icon == null) {
			if (is.getItem() instanceof IIaSTool) {
				final IaSToolMaterial mat = IaSToolMaterial.extractMaterial(is);
				icon = mat.getIcon(is);
			}
			if (icon == null) {
				GL11.glPopMatrix();
				return;
			}
		}

		final boolean blendWasEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
		final boolean lightingWasEnabled = GL11.glIsEnabled(GL11.GL_LIGHTING);
		if (doGlowTransforms) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			final int j = Char.MaxValue();
			final int k = 0;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		}
		final TextureManager texturemanager = mc.getTextureManager();
		texturemanager.bindTexture(texturemanager.getResourceLocation(is.getItemSpriteNumber()));
		final Tessellator tessellator = Tessellator.instance;
		final float minU = icon.getMinU();
		final float maxU = icon.getMaxU();
		final float minV = icon.getMinV();
		final float maxV = icon.getMaxV();
		if (type != ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.0F, -0.3F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			final float f6 = 1.5F;
			GL11.glScalef(f6, f6, f6);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		} else {
			GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
			if (RenderItem.renderInFrame)
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
			/*
			 * GL11.glTranslatef(0.0F, 0.70F, 0.0F); GL11.glRotatef(90f, 0, 0, -1f);
			 */
		}
		final float width = 0.0625F;
		final float zshift = -width / 8;
		int count = 1;
		if (type == ItemRenderType.ENTITY)
			count += MathHelper.calculateLogBaseTwo(is.stackSize);

		final Random random = new Random(Item.getIdFromItem(is.getItem()));
		GL11.glTranslatef(0, 0, (((width + zshift) / 2) * (count)));

		final int chroma = is.getItem().getColorFromItemStack(is, pass);
		final float r = (chroma >> 16 & 255) / 255.0F;
		final float g = (chroma >> 8 & 255) / 255.0F;
		final float b = (chroma & 255) / 255.0F;
		GL11.glColor4f(r, g, b, 1.0F);

		for (int i = 0; i < count; ++i) {
			final float x = (random.nextFloat() * 2.0F - 1.0F) * 0.2F * (1 + is.stackSize / 64);
			final float y = (random.nextFloat() * 2.0F - 1.0F) * 0.1F * (1 + is.stackSize / 64);
			random.nextFloat();
			if (i > 0)
				GL11.glTranslatef(x, -y, -(width + zshift));
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(),
					width);
			if (i > 0)
				GL11.glTranslatef(-x, y, 0);
		}

		if (doGlowTransforms) {
			if (!blendWasEnabled)
				GL11.glDisable(GL11.GL_BLEND);
			if (lightingWasEnabled)
				GL11.glEnable(GL11.GL_LIGHTING);
		}

		if (is.hasEffect(pass)) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			texturemanager.bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			final float f7 = 0.76F;
			GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			final float f8 = 0.125F;
			GL11.glScalef(f8, f8, f8);
			float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
			GL11.glTranslatef(f9, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f8, f8, f8);
			f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
			GL11.glTranslatef(-f9, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			if (!blendWasEnabled)
				GL11.glDisable(GL11.GL_BLEND);
			if (lightingWasEnabled)
				GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		if (type != ItemRenderType.ENTITY)
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
			GL11.glPopMatrix();

		final Entity entity = (Entity) data[1];

		boolean doGlowTransforms = false;
		if (item.getItem() instanceof IIaSGlowing && !doGlowTransforms)
			doGlowTransforms = ((IIaSGlowing) item.getItem()).getFirstGlowPass(item) <= 0;

		renderItem(entity, item, 0, mc.entityRenderer.itemRenderer, doGlowTransforms, type);

		if (item.getItem().requiresMultipleRenderPasses())
			for (int x = 1; x < item.getItem().getRenderPasses(item.getItemDamage()); x++) {
				if (item.getItem() instanceof IIaSGlowing && !doGlowTransforms)
					doGlowTransforms = x >= ((IIaSGlowing) item.getItem()).getFirstGlowPass(item);
				renderItem(entity, item, x, mc.entityRenderer.itemRenderer, doGlowTransforms, type);
				if (doGlowTransforms && x == item.getItem().getRenderPasses(item.getItemDamage()) - 1)
					;
				// GL11.glDisable(GL11.GL_BLEND);
			}

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED)
			GL11.glPushMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY
				&& (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}
}