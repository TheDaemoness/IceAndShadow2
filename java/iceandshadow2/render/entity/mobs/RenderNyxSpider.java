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
public class RenderNyxSpider extends RenderLiving
{
    private static final ResourceLocation field_110891_a = new ResourceLocation("iceandshadow2:textures/mob/spiderwisp_eyes.png");
    private static final ResourceLocation field_110890_f = new ResourceLocation("iceandshadow2:textures/mob/spiderwisp.png");

    public RenderNyxSpider()
    {
        super(new ModelSpider(), 1.0F);
        this.setRenderPassModel(new ModelSpider());
        this.shadowSize *= 0.8;
    }

    protected float setSpiderDeathMaxRotation(EntityNyxSpider par1EntitySpider) {
        return 180.0F;
    }

    protected void scaleSpider(EntityNyxSpider par1EntityCaveSpider, float par2)
    {
        GL11.glScalef(0.8F, 0.7F, 0.8F);
    }
    
    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDepthMask(true);
        super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
        this.func_110827_b(par1EntityLiving, par2, par4, par6, par8, par9);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
   
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
    	super.preRenderCallback(par1EntityLivingBase, par2);
        this.scaleSpider((EntityNyxSpider)par1EntityLivingBase, par2);
    }

    /**
     * Sets the spider's glowing eyes
     */
    protected int setSpiderEyeBrightness(EntityNyxSpider par1EntitySpider, int par2, float par3)
    {
    	
        if ((par2 != 0) || par1EntitySpider.isInvisible())
        {
            return -1;
        }
        else
        {
        	this.bindTexture(field_110891_a);
            float f1 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDepthMask(true);
            
            char c0 = 61680;
            int j = c0 % 65536;
            int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return 1;
        }
    }

    protected ResourceLocation func_110889_a(EntityNyxSpider par1EntitySpider)
    {
        return field_110890_f;
    }

    @Override
    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
    {
        return this.setSpiderDeathMaxRotation((EntityNyxSpider)par1EntityLivingBase);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.setSpiderEyeBrightness((EntityNyxSpider)par1EntityLivingBase, par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110889_a((EntityNyxSpider)par1Entity);
    }
}
