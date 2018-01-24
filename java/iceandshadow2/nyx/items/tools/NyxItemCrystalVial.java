package iceandshadow2.nyx.items.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.nyx.NyxItems;

public class NyxItemCrystalVial extends IaSBaseItemMulti implements IIaSApiTransmute {

	@SideOnly(Side.CLIENT)
	protected IIcon extractor;

	public NyxItemCrystalVial(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return extractor;
		return itemIcon;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != this)
			return 0;
		if (target.getItemDamage() == 1)
			if (catalyst.getItem() == NyxItems.toxicCore && catalyst.getItemDamage() == 0)
				return 160;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		List<ItemStack> li = null;
		--target.stackSize;
		if (catalyst.getItem() == NyxItems.toxicCore) {
			li = new ArrayList<ItemStack>();
			catalyst.stackSize -= 1;
			li.add(new ItemStack(NyxItems.extractorPoison));
		}
		return li;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		extractor = reg.registerIcon(getTextureName() + "Extractor");
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
