package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;

public class NyxItemCortra extends IaSBaseItemMulti implements IIaSApiTransmute {

	@SideOnly(Side.CLIENT)
	protected IIcon crystalIcon;
	
	public NyxItemCortra(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addSmelting(new ItemStack(this,1,0), new ItemStack(this,1,1), 0);
		GameRegistry.addShapelessRecipe(new ItemStack(this,1,0), new ItemStack(this,1,1));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return crystalIcon;
		return this.itemIcon;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getTexName() + "Dust");
		this.crystalIcon = reg.registerIcon(this.getTexName() + "Crystal");
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() != NyxItems.echirIngot || target.getItemDamage() != 1 || 
				catalyst.getItem() != this || catalyst.getItemDamage() != 0)
			return 0;
		return 120;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World world) {
		List<ItemStack> it = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		it.add(new ItemStack(NyxItems.cortraIngot,Math.min(2, target.stackSize),1));
		target.stackSize -= Math.min(2, target.stackSize);
		return it;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}
}
