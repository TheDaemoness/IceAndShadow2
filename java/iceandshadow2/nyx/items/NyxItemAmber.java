package iceandshadow2.nyx.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxItemAmber extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	public NyxItemAmber(String texName) {
		super(EnumIaSModule.NYX, texName);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return stack.getItemDamage()==1;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return stack.getItemDamage()==1?32:0;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.getItemDamage()>0?1:4;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() == this && target.getItemDamage() == 0 && catalyst.hasTagCompound()) {
			final NBTTagCompound tg = catalyst.getTagCompound();
			if (tg.hasKey("ench"))
				return 120;
		} else if (catalyst.getItem() == this && catalyst.getItemDamage() == 1 && target.isItemEnchantable())
			return 120;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		if (target.getItem() == this && target.getItemDamage() == 0) {
			final Map<Integer, Integer> ench = EnchantmentHelper.getEnchantments(catalyst);
			synchronized (ench) { // Dodge concurrent access issues?
				for (final Integer i : ench.keySet())
					if (ench.get(i).intValue() <= 1) {
						ench.remove(i);
					} else {
						ench.put(i, ench.get(i) - 1);
					}
				EnchantmentHelper.setEnchantments(ench, target);
				EnchantmentHelper.setEnchantments(ench, catalyst);
				if (!ench.isEmpty()) {
					target.setItemDamage(1);
				}
			}
		} else if (catalyst.getItem() == this && catalyst.getItemDamage() == 1) {
			final Map<Integer, Integer> ench = new HashMap<Integer, Integer>(
					EnchantmentHelper.getEnchantments(catalyst));
			synchronized (ench) { // Dodge concurrent access issues?
				EnchantmentHelper.setEnchantments(ench, target);
				for (final Integer i : ench.keySet())
					if (ench.get(i).intValue() <= 1) {
						ench.remove(i);
					} else {
						ench.put(i, ench.get(i) - 1);
					}
				EnchantmentHelper.setEnchantments(ench, catalyst);
				if(ench.isEmpty()) {
					catalyst.setItemDamage(0);
				}
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack is, int pass) {
		return is.getItemDamage() == 1;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity pos) {
		if(world.rand.nextBoolean()) {
			IaSFxManager.spawnParticle(world, "vanilla_spell", pos.posX - 0.1 + world.rand.nextDouble() / 5,
				pos.posY - 0.2 - world.rand.nextDouble() / 3, pos.posZ - 0.1 + world.rand.nextDouble() / 5,
				-0.025 + world.rand.nextDouble() / 20, -0.05F, -0.025 + world.rand.nextDouble() / 20, false, false);
		}
		return true;
	}

}
