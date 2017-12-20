package iceandshadow2.nyx.items.tools;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.ias.items.IaSBaseItemMultiTextured;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.tools.NyxItemPotion;
import iceandshadow2.nyx.world.NyxBiomes;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NyxItemFlask extends IaSBaseItemMultiTextured implements IIaSApiTransmute {

	public NyxItemFlask(String id) {
		super(EnumIaSModule.NYX, id, 1);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() == this && target.getItemDamage() == 0 && catalyst.getItem() instanceof ItemPotion) {
			return (NyxItemPotion.convertVanilla(catalyst.getItemDamage()) >= 0)?80:0;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final int whatpot = catalyst.getItemDamage();
		catalyst.func_150996_a(Items.glass_bottle);
		catalyst.setItemDamage(0);
		target.stackSize -= 1;
		return Arrays.asList(new ItemStack(NyxItems.potion, 1, NyxItemPotion.convertVanilla(whatpot)));
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
