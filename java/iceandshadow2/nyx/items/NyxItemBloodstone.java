package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBloodstone extends IaSItemFood implements IIaSGlowing, IIaSOnDeathDrop, IIaSApiTransmute {

	public NyxItemBloodstone(String texName) {
		super(EnumIaSModule.NYX, texName, -20, 0.0F, false);
		setAlwaysEdible();
		setMaxStackSize(4);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 16;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != this)
			return 0;
		if (target.getItem() instanceof ItemBlock) {
			final Block bl = ((ItemBlock) target.getItem()).field_150939_a;
			if (bl == Blocks.obsidian)
				return 160;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		--catalyst.stackSize;
		int i = 0;
		for (; i < 3; ++i) {
			if (target.stackSize > 0)
				--target.stackSize;
			else
				break;
		}
		List<ItemStack> retval = new ArrayList<ItemStack>();
		retval.add(new ItemStack(NyxBlocks.cryingObsidian, i));
		return retval;
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		if(!par2World.isRemote) {
			player.clearActivePotions();
			IaSEntityHelper.spawnNourishment(player, (int)(player.getHealth()*2));
			player.attackEntityFrom(DamageSource.outOfWorld, player.getMaxHealth() + 2*player.getHealth());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
