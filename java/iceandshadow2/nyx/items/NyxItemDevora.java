package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemDevora extends IaSBaseItemMulti implements IIaSGlowing {

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
					if (!par3EntityPlayer.capabilities.isCreativeMode)
						--par1ItemStack.stackSize;
				}
			}
		return par1ItemStack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTexName());
		smallIcon = reg.registerIcon(getTexName() + "Small");
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

}
