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
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

public class RenderNyxTeTransmutationAltar extends TileEntitySpecialRenderer {

	private EntityItem cat, tar;
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x,
			double y, double z, float eh) {
        GL11.glPushMatrix();
        //This will move our renderer so that it will be on proper place in the world
        GL11.glTranslatef((float)x+0.5F, (float)y, (float)z+0.5F);
        NyxTeTransmutationAltar alt = (NyxTeTransmutationAltar)te;
        
        if(cat == null && alt.catalyst != null)
        	cat = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.catalyst);
        else if(cat != null && alt.catalyst == null)
        	cat = null;
        
        if(tar == null && alt.target != null)
        	tar = new EntityItem(Minecraft.getMinecraft().theWorld, 0D, 0D, 0D, alt.target);
        else if(tar != null && alt.target == null)
        	tar = null;
        
        if(cat != null) {
            this.cat.hoverStart = 0.0F;
            GL11.glTranslatef(0.0F, 1.25F, 0.0F);
            GL11.glPushMatrix();
            RenderManager.instance.renderEntityWithPosYaw(this.cat, 0.0D, 0.0D, 0.0D, 0.0F, Minecraft.getMinecraft().theWorld.getTotalWorldTime());
            GL11.glPopMatrix();
            GL11.glTranslatef(0.0F, -1.25F, 0.0F);
        }
        if(tar != null) {
            this.tar.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glPushMatrix();
            if(alt.target.getItem() instanceof ItemBlock)
            	GL11.glTranslatef(0.0F, 0.8F, -0.175F);
            else
            	GL11.glTranslatef(0.0F, 0.8F, -0.35F);
            GL11.glRotatef(180, 0, 1, 1);
            RenderManager.instance.renderEntityWithPosYaw(this.tar, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
            RenderItem.renderInFrame = false;
        }
        GL11.glPopMatrix();
	}

}
