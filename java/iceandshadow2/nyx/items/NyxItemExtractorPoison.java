package iceandshadow2.nyx.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.world.NyxTeleporter;
import iceandshadow2.util.IaSPlayerHelper;

public class NyxItemExtractorPoison extends IaSBaseItemSingle implements IIaSApiTransmutable {

	@SideOnly(Side.CLIENT)
	protected IIcon fillIcons[];
	
	public NyxItemExtractorPoison(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(1);
		this.setMaxDamage(14);
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if(dmg == 0)
			return this.itemIcon;
		if(dmg >= 13)
			return fillIcons[6];
		return fillIcons[(dmg-1)/2];
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.itemIcon = r.registerIcon(this.getTexName()+"0");
		fillIcons = new IIcon[7];
		for(int i = 1; i <= 7; ++i)
			fillIcons[i-1] = r.registerIcon(this.getTexName()+i);
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
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.removePotionEffect(Potion.poison.id);
		is.setItemDamage(is.getItemDamage()+1);
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order,
			EntityPlayer pwai) {
		if(pwai.isPotionActive(Potion.poison) && heap.getItemDamage() < this.getMaxDamage()-1)
			pwai.setItemInUse(heap, this.getMaxItemUseDuration(heap));
		return heap;
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if(catalyst.getItem() != this || target.getItem() != NyxItems.draconium)
			return 0;
		return 35;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		catalyst.setItemDamage(0);
		catalyst.func_150996_a(NyxItems.crystalVial);
		target.stackSize += 1;
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}
}
