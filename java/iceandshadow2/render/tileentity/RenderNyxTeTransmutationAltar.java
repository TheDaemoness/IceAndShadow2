package iceandshadow2.render.tileentity;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

public class RenderNyxTeTransmutationAltar extends TileEntitySpecialRenderer {

	private EntityItem cat, tar;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float eh) {
		GL11.glPushMatrix();
		// This will move our renderer so that it will be on proper place in the
		// world
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		final NyxTeTransmutationAltar alt = (NyxTeTransmutationAltar) te;

		if (cat == null && alt.catalyst != null) {
			cat = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.catalyst);
		} else if (cat != null)
			if (alt.catalyst == null) {
				cat = null;
			} else if (alt.catalyst.getItem() != cat.getEntityItem().getItem()) {
				cat.setEntityItemStack(alt.catalyst);
			} else if (!alt.catalyst.getItem().isDamageable()
					&& alt.catalyst.getItemDamage() != cat.getEntityItem().getItemDamage()) {
				cat.setEntityItemStack(alt.catalyst);
			} else if (alt.catalyst.stackSize != cat.getEntityItem().stackSize) {
				cat.setEntityItemStack(alt.catalyst);
			}

		if (tar == null && alt.target != null) {
			tar = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.target);
		} else if (tar != null)
			if (alt.target == null) {
				tar = null;
			} else if (alt.target.getItem() != tar.getEntityItem().getItem()) {
				tar.setEntityItemStack(alt.target);
			} else if (!alt.target.getItem().isDamageable()
					&& alt.target.getItemDamage() != tar.getEntityItem().getItemDamage()) {
				tar.setEntityItemStack(alt.target);
			} else if (alt.target.stackSize != tar.getEntityItem().stackSize) {
				tar.setEntityItemStack(alt.target);
			}

		if (cat != null) {
			cat.hoverStart = 0.0F;
			GL11.glTranslatef(0.0F, 1.25F, 0.0F);
			GL11.glPushMatrix();
			RenderManager.instance.renderEntityWithPosYaw(cat, 0.0D, 0.0D, 0.0D, 0.0F,
					Minecraft.getMinecraft().theWorld.getTotalWorldTime());
			GL11.glPopMatrix();
			GL11.glTranslatef(0.0F, -1.25F, 0.0F);
		}
		if (tar != null) {
			tar.hoverStart = 0.0F;
			RenderItem.renderInFrame = true;
			GL11.glPushMatrix();
			final float height = (float)NyxBlocks.transmutationAltar.getBlockBoundsMaxY();
			float bias = 0;
			boolean asitem = false;
			if (alt.target.getItem() instanceof ItemBlock) {
				final ItemBlock ib = (ItemBlock)alt.target.getItem();
				bias = (float)ib.field_150939_a.getBlockBoundsMaxY()/10;
				asitem = !RenderBlocks.renderItemIn3d(ib.field_150939_a.getRenderType());
			} else {
				asitem = true;
			}
			if (asitem) {
				GL11.glTranslatef(0.0F, height+0.025f, -0.225F);
				GL11.glRotatef(180.F, 0, 1, 1);
			} else {
				GL11.glTranslatef(0.0F, height-bias, 0);
			}
			RenderManager.instance.renderEntityWithPosYaw(tar, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
			RenderItem.renderInFrame = false;
		}
		GL11.glPopMatrix();
	}

}
