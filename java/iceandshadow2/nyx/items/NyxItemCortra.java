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
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.nyx.NyxItems;

public class NyxItemCortra extends IaSBaseItemMultiTexturedGlow implements IIaSApiTransmute {

	public NyxItemCortra(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addSmelting(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 0);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1));
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != NyxItems.echirIngot || target.getItemDamage() != 1 || catalyst.getItem() != this
				|| catalyst.getItemDamage() != 0)
			return 0;
		return 120;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List<ItemStack> it = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		it.add(new ItemStack(NyxItems.cortraIngot, Math.min(2, target.stackSize), 1));
		target.stackSize -= Math.min(2, target.stackSize);
		return it;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
