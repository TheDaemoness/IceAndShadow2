package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemEchirArmorActive extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	protected int slot;

	public IaSItemEchirArmorActive(String texName, int slut) { // Wat?
		super(EnumIaSModule.IAS, texName);
		slot = slut; // Oh...
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString()
				+ "Sneak and Use Item to finalize.");
	}

	@Override
	public int getMaxDamage() {
		return IaSTools.armorEchir[slot].getMaxDamage();
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != this || target.isItemDamaged())
			return 0;
		if (catalyst.getItem() == NyxItems.cortra || catalyst.getItem() == NyxItems.navistraShard)
			switch (slot) {
			case 0:
				return catalyst.stackSize >= 5 ? 375 : 0;
			case 1:
				return catalyst.stackSize >= 8 ? 600 : 0;
			case 2:
				return catalyst.stackSize >= 7 ? 525 : 0;
			case 3:
				return catalyst.stackSize >= 4 ? 300 : 0;
			}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final double percentage = 0.5 + (0.5 * target.getItemDamage() / target.getMaxDamage());
		if (catalyst.getItem() == NyxItems.cortra)
			target.func_150996_a(IaSTools.armorCortra[slot]);
		if (catalyst.getItem() == NyxItems.navistraShard)
			target.func_150996_a(IaSTools.armorNavistra[slot]);
		target.setItemDamage((int) (target.getMaxDamage() * percentage) - 1);
		switch (slot) {
		case 0:
			catalyst.stackSize -= 5;
			break;
		case 1:
			catalyst.stackSize -= 8;
			break;
		case 2:
			catalyst.stackSize -= 7;
			break;
		case 3:
			catalyst.stackSize -= 4;
			break;
		}
		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w, EntityPlayer hulk) {
		if (hulk.isSneaking()) // Does not always evaluate to false, see that
			// one jumpscare in The Avengers.
			par1 = new ItemStack(IaSTools.armorEchir[slot], 1, par1.getItemDamage());
		else
			IaSPlayerHelper.messagePlayer(hulk, "It's probably not safe to wear this while it's primed.");
		return par1;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
