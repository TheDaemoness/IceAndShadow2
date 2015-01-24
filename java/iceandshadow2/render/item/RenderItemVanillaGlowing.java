package iceandshadow2.render.item;

import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

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
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON
				|| type == ItemRenderType.EQUIPPED
				|| type == ItemRenderType.ENTITY;
	}

	public void renderItem(Entity entity, ItemStack item, int pass,
			ItemRenderer rendr, boolean doGlowTransforms, ItemRenderType type) {

		GL11.glPushMatrix();

		IIcon icon;
		if (entity instanceof EntityLivingBase)
			icon = ((EntityLivingBase) entity).getItemIcon(item, pass);
		else
			icon = item.getItem().getIcon(item, pass);

		if (icon == null) {
			if (item.getItem() instanceof IIaSTool) {
				final IaSToolMaterial mat = IaSToolMaterial
						.extractMaterial(item);
				icon = mat.getIcon(item);
			}
			if (icon == null) {
				GL11.glPopMatrix();
				return;
			}
		}

		if (doGlowTransforms) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			final int j = Char.MaxValue();
			final int k = 0;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
					j / 1.0F, k / 1.0F);
		}
		final TextureManager texturemanager = this.mc.getTextureManager();
		texturemanager.bindTexture(texturemanager.getResourceLocation(item
				.getItemSpriteNumber()));
		final Tessellator tessellator = Tessellator.instance;
		final float f = icon.getMinU();
		final float f1 = icon.getMaxU();
		final float f2 = icon.getMinV();
		final float f3 = icon.getMaxV();
		final float f4 = 0.0F;
		final float f5 = 0.3F;
		if (type != ItemRenderType.ENTITY) {
			GL11.glTranslatef(-f4, -f5, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			final float f6 = 1.5F;
			GL11.glScalef(f6, f6, f6);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		} else {
			GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
			if(RenderItem.renderInFrame) {
				GL11.glTranslatef(0.0F, 0.70F, 0.0F);
				GL11.glRotatef(90.0F, 0.0F, 0.0F, -1.0F);
			}
		}
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3,
				icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

		if (doGlowTransforms) {
			// GL11.glDisable(GL11.GL_BLEND); //Find a way to re-enable this
			// later.
			GL11.glEnable(GL11.GL_LIGHTING);
		}

		if (item.hasEffect(pass)) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			texturemanager.bindTexture(new ResourceLocation(
					"textures/misc/enchanted_item_glint.png"));
			if (!doGlowTransforms) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}
			final float f7 = 0.76F;
			GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			final float f8 = 0.125F;
			GL11.glScalef(f8, f8, f8);
			float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
			GL11.glTranslatef(f9, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F,
					256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f8, f8, f8);
			f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
			GL11.glTranslatef(-f9, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F,
					256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			if (!doGlowTransforms)
				GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		if (type != ItemRenderType.ENTITY) {
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON
				|| type == ItemRenderType.EQUIPPED)
			GL11.glPopMatrix();

		final Entity entity = (Entity) data[1];

		boolean doGlowTransforms = false;
		if (item.getItem() instanceof IIaSGlowing && !doGlowTransforms)
			doGlowTransforms = ((IIaSGlowing) item.getItem())
					.getFirstGlowPass(item) <= 0;

		renderItem(entity, item, 0, this.mc.entityRenderer.itemRenderer,
				doGlowTransforms, type);

		if (item.getItem().requiresMultipleRenderPasses()) {
			for (int x = 1; x < item.getItem().getRenderPasses(
					item.getItemDamage()); x++) {
				if (item.getItem() instanceof IIaSGlowing && !doGlowTransforms)
					doGlowTransforms = x >= ((IIaSGlowing) item.getItem())
							.getFirstGlowPass(item);
				renderItem(entity, item, x,
						this.mc.entityRenderer.itemRenderer, doGlowTransforms,
						type);
				if (doGlowTransforms
						&& x == item.getItem().getRenderPasses(
								item.getItemDamage()) - 1)
					;
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON
				|| type == ItemRenderType.EQUIPPED)
			GL11.glPushMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY
				&& (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}
}