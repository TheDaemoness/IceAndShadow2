package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSApiTransmute;
import iceandshadow2.ias.api.IIaSDescriptive;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBloodstone extends IaSItemFood implements IIaSGlowing, IIaSApiTransmute {

	public NyxItemBloodstone(String texName) {
		super(EnumIaSModule.NYX, texName, -20, 0.0F, false);
		setAlwaysEdible();
		setMaxStackSize(4);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.STYX;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 8;
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
		} else if (target.getItem() == NyxItems.cortra && target.getItemDamage() == 0)
			return 80;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		--catalyst.stackSize;
		final List<ItemStack> retval = new ArrayList<ItemStack>();
		int i = 0;
		for (; i < 3 && target.stackSize > 0; ++i) {
			--target.stackSize;
		}
		if(target.getItem() == NyxItems.cortra) {
			retval.add(new ItemStack(NyxItems.draconium, i*2, 1));
		} else {
			retval.add(new ItemStack(NyxBlocks.sanguineObsidian, i));
		}
		return retval;
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		if (!par2World.isRemote) {
			player.clearActivePotions();
			IaSEntityHelper.spawnNourishment(player, (int) (player.getHealth() * 2));
			player.attackEntityFrom(IaSDamageSources.dmgDrain, player.getMaxHealth() + 2 * player.getHealth());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		if(world.rand.nextBoolean()) {
			IaSFxManager.spawnParticle(world, "dripBlood", ent.posX+0.05+world.rand.nextFloat()/10, ent.posY+0.125+world.rand.nextFloat()/4, ent.posZ+0.05+world.rand.nextFloat()/10, false, false);
		}
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public String getUnlocalizedDescription(EntityPlayer entityPlayer, ItemStack is) {
		return this.getModName();
	}

	@Override
	public boolean isHintWarning(EntityPlayer entityPlayer, ItemStack itemStack) {
		return true;
	}

	@Override
	public String getUnlocalizedHint(EntityPlayer entityPlayer, ItemStack itemStack) {
		return this.getModName();
	}
}
