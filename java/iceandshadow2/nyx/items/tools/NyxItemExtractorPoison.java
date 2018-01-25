package iceandshadow2.nyx.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemExtractorPoison extends IaSBaseItemSingle implements IIaSApiTransmute {

	@SideOnly(Side.CLIENT)
	protected IIcon fillIcons[];

	public NyxItemExtractorPoison(String texName) {
		super(EnumIaSModule.NYX, texName);
		setMaxStackSize(1);
		setMaxDamage(14);
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 0)
			return itemIcon;
		if (dmg >= 13)
			return fillIcons[6];
		return fillIcons[(dmg - 1) / 2];
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 16;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		return null;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.removePotionEffect(Potion.poison.id);
		is.setItemDamage(is.getItemDamage() + 1);
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order, EntityPlayer pwai) {
		if (pwai.isPotionActive(Potion.poison) && heap.getItemDamage() < this.getMaxDamage() - 1) {
			pwai.setItemInUse(heap, getMaxItemUseDuration(heap));
		}
		return heap;
	}

	@Override
	public void registerIcons(IIconRegister r) {
		itemIcon = r.registerIcon(getTextureName() + "0");
		fillIcons = new IIcon[7];
		for (int i = 1; i <= 7; ++i) {
			fillIcons[i - 1] = r.registerIcon(getTextureName() + i);
		}
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
