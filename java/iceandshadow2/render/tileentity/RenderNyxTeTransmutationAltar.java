package iceandshadow2.render.tileentity;

import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
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

		if (this.cat == null && alt.catalyst != null)
			this.cat = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.catalyst);
		else if (this.cat != null) {
			if (alt.catalyst == null)
				this.cat = null;
			else if (alt.catalyst.getItem() != this.cat.getEntityItem().getItem())
				this.cat.setEntityItemStack(alt.catalyst);
			else if (!alt.catalyst.getItem().isDamageable()
					&& alt.catalyst.getItemDamage() != this.cat.getEntityItem().getItemDamage())
				this.cat.setEntityItemStack(alt.catalyst);
			else if (alt.catalyst.stackSize != this.cat.getEntityItem().stackSize)
				this.cat.setEntityItemStack(alt.catalyst);
		}

		if (this.tar == null && alt.target != null)
			this.tar = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.target);
		else if (this.tar != null) {
			if (alt.target == null)
				this.tar = null;
			else if (alt.target.getItem() != this.tar.getEntityItem().getItem())
				this.tar.setEntityItemStack(alt.target);
			else if (!alt.target.getItem().isDamageable()
					&& alt.target.getItemDamage() != this.tar.getEntityItem().getItemDamage())
				this.tar.setEntityItemStack(alt.target);
			else if (alt.target.stackSize != this.tar.getEntityItem().stackSize)
				this.tar.setEntityItemStack(alt.target);
		}

		if (this.cat != null) {
			this.cat.hoverStart = 0.0F;
			GL11.glTranslatef(0.0F, 1.25F, 0.0F);
			GL11.glPushMatrix();
			RenderManager.instance.renderEntityWithPosYaw(this.cat, 0.0D, 0.0D, 0.0D, 0.0F,
					Minecraft.getMinecraft().theWorld.getTotalWorldTime());
			GL11.glPopMatrix();
			GL11.glTranslatef(0.0F, -1.25F, 0.0F);
		}
		if (this.tar != null) {
			this.tar.hoverStart = 0.0F;
			RenderItem.renderInFrame = true;
			GL11.glPushMatrix();
			if (alt.target.getItem() instanceof ItemBlock) {
				GL11.glRotatef(180.F, 0, 0, 1);
				GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			} else {
				GL11.glTranslatef(0.0F, 0.8F, -0.225F);
				GL11.glRotatef(180.F, 0, 1, 1);
			}
			RenderManager.instance.renderEntityWithPosYaw(this.tar, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glPopMatrix();
			RenderItem.renderInFrame = false;
		}
		GL11.glPopMatrix();
	}

}
