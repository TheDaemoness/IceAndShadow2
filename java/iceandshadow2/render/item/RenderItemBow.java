package iceandshadow2.render.item;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderItemBow extends RenderItemVanillaGlowing {
	private boolean lb;

	public RenderItemBow(boolean isLongbow) {
		lb = isLongbow;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		if(type == ItemRenderType.EQUIPPED) {
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
			GL11.glPushMatrix();
		}
		
		super.renderItem(type, item, data);
		
		if(type == ItemRenderType.EQUIPPED) {
			GL11.glPopMatrix();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
		}
	}
}