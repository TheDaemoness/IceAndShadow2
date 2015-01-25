package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemExousicIce extends IaSBaseItemSingle implements IIaSApiTransmutable {

	public NyxItemExousicIce(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if(catalyst.getItem() != this)
			return 0;
		if(target.getItem() instanceof ItemBlock) {
			Block bl = ((ItemBlock)target.getItem()).field_150939_a;
			if(bl == NyxBlocks.brickFrozen && catalyst.stackSize >= 3)
				return 25;
			if(bl == NyxBlocks.brickPaleCracked)
				return 25;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		ArrayList<ItemStack> li = new ArrayList<ItemStack>();
		int quant = Math.min(target.stackSize,catalyst.stackSize);
		if(target.getItem() instanceof ItemBlock) {
			if(((ItemBlock)target.getItem()).field_150939_a == NyxBlocks.brickPaleCracked) {
				catalyst.stackSize -= 1*quant;
				li.add(new ItemStack(NyxBlocks.brickPale,quant));
			}
			else {
				quant = Math.min(target.stackSize,catalyst.stackSize/3);
				catalyst.stackSize -= 3*quant;
				li.add(new ItemStack(NyxBlocks.brickPale,quant*2));
			}
			target.stackSize -= 1*quant;
		}
		return li;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		// TODO Auto-generated method stub
		return false;
	}
}