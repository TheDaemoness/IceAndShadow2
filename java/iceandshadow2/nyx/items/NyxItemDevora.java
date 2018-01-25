package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemDevora extends IaSBaseItemMulti implements IIaSGlowing, IIaSApiTransmute {

	@SideOnly(Side.CLIENT)
	protected IIcon smallIcon;

	public NyxItemDevora(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 8, 1), new ItemStack(this, 1, 0));
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return smallIcon;
		return itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote)
			return par1ItemStack;

		final MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
				true);

		if (movingobjectposition != null)
			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK && par1ItemStack.getItemDamage() == 0) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				final ForgeDirection f = ForgeDirection.getOrientation(movingobjectposition.sideHit);

				if (par2World.getBlock(i, j, k).isReplaceable(par2World, i, j, k)) {
					// No-op.
				} else if (par2World.getBlock(i + f.offsetX, j + f.offsetY, k + f.offsetZ).isReplaceable(par2World,
						i + f.offsetX, j + f.offsetY, k + f.offsetZ)) {
					i += f.offsetX;
					j += f.offsetY;
					k += f.offsetZ;
				} else
					return par1ItemStack;
				if (par2World.getBlock(i, j - 1, k).isSideSolid(par2World, i, j - 1, k, ForgeDirection.UP)) {
					par2World.setBlock(i, j, k, NyxBlocks.unstableDevora);
					if (!par3EntityPlayer.capabilities.isCreativeMode) {
						--par1ItemStack.stackSize;
					}
				}
			}
		return par1ItemStack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		smallIcon = reg.registerIcon(getTextureName() + "Small");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() == catalyst.getItem() && catalyst.getItem() == this && catalyst.getItemDamage() == 1) {
			final int total = catalyst.stackSize+(target.getItemDamage() == 1?target.stackSize:0);
			if(total > 8)
				return total*8;
		}
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>(1);
		if(target.getItemDamage() == 0) {
			final int count = catalyst.stackSize/8;
			catalyst.stackSize -= count*8;
			ret.add(new ItemStack(this, count+target.stackSize));
			target.stackSize = 0;
		} else {
			final int totalstack = target.stackSize + catalyst.stackSize;
			catalyst.stackSize = 0;
			final int count = totalstack/8;
			ret.add(new ItemStack(this, count));
			target.stackSize = totalstack%8;
		}
		return ret;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

}
