package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemHeat extends IaSBaseItemMulti implements IIaSGlowing, IIaSApiTransmutable {

	@SideOnly(Side.CLIENT)
	protected IIcon icons[];

	public NyxItemHeat(String texName) {
		super(EnumIaSModule.NYX, texName, 4);
		GameRegistry.addShapelessRecipe(new ItemStack(this,8,1), new ItemStack(this,1,0));
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
	
	@Override
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if(meta < this.icons.length)
			return this.icons[meta];
		return this.icons[1];
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.icons = new IIcon[4];
		for(int i = 0; i < this.icons.length; ++i)
			this.icons[i] = reg.registerIcon(this.getTexName()+i);
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if(FurnaceRecipes.smelting().getSmeltingResult(target) != null)
			return 160*(catalyst.getItemDamage()+1);
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		List<ItemStack> li = new ArrayList<ItemStack>();
		int time;
		if(catalyst.getItemDamage() > 3)
			time = 0;
		else
			time = catalyst.getItemDamage();
		ItemStack ret = FurnaceRecipes.smelting().getSmeltingResult(target).copy();
		ret.stackSize = (int)Math.min(Math.pow(4, time),target.stackSize);
		ret.stackSize = Math.min(ret.getMaxStackSize(),ret.stackSize);
		li.add(ret);
		target.stackSize -= ret.stackSize;
		--catalyst.stackSize;
		return li;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity pos) {
		IaSFxManager.spawnParticle(world, "vanilla_flame", 
				pos.posX-0.1+world.rand.nextDouble()/5, 
				pos.posY+world.rand.nextDouble()/3, 
				pos.posZ-0.1+world.rand.nextDouble()/5, 
				-0.05+world.rand.nextDouble()/10,
				-0.1F, 
				-0.05+world.rand.nextDouble()/10, 
				false, false);
		return true;
	}

}
