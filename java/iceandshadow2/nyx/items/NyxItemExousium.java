package iceandshadow2.nyx.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.util.EnumIaSModule;

public class NyxItemExousium extends IaSBaseItemMulti implements IIaSGlowing {

	@SideOnly(Side.CLIENT)
	protected IIcon dustIconGlow, rockIcon[], crystalIcon[];
	
	public NyxItemExousium(String texName) {
		super(EnumIaSModule.NYX, texName, 3);
		GameRegistry.addShapelessRecipe(new ItemStack(this,1,1), 
				new ItemStack(this,1,0), new ItemStack(this,1,0),
				new ItemStack(this,1,0), new ItemStack(this,1,0));
		GameRegistry.addShapelessRecipe(new ItemStack(this,1,2), 
				new ItemStack(this,1,1), new ItemStack(this,1,1), 
				new ItemStack(this,1,1), new ItemStack(this,1,1));
		GameRegistry.addShapelessRecipe(new ItemStack(this,4,1), new ItemStack(this,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(this,4,0), new ItemStack(this,1,1));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int dmg, int pass) {
		if(dmg == 2)
			return crystalIcon[pass];
		if(dmg == 1)
			return rockIcon[pass];
		if(pass == 0)
			return dustIconGlow;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.rockIcon = new IIcon[2];
		this.crystalIcon = new IIcon[2];
		
		this.itemIcon = reg.registerIcon(this.getTexName()+"Dust");
		this.rockIcon[1] = reg.registerIcon(this.getTexName()+"Rock");
		this.crystalIcon[1] = reg.registerIcon(this.getTexName()+"Crystal");
		
		this.dustIconGlow = reg.registerIcon(this.getTexName()+"DustGlow");
		this.rockIcon[0] = reg.registerIcon(this.getTexName()+"RockGlow");
		this.crystalIcon[0] = reg.registerIcon(this.getTexName()+"CrystalGlow");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

}
