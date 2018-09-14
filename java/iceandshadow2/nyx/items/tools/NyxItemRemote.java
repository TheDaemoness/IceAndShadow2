package iceandshadow2.nyx.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.api.IIaSSignalReceiverBlock;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.tools.IaSBaseItemLocational;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemRemote extends IaSBaseItemLocational implements IIaSGlowing {

	public static boolean isPressed(ItemStack is) {
		return IntBits.areAllSet(is.getItemDamage(), 2);
	}

	@SideOnly(Side.CLIENT)
	protected IIcon pressed;

	public NyxItemRemote(String par1) {
		super(EnumIaSModule.NYX, par1);
	}

	@Override
	public boolean canBindTo(World w, int x, int y, int z) {
		return w.getBlock(x, y, z) instanceof IIaSSignalReceiverBlock;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (isPressed(stack))
			return pressed;
		return itemIcon;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.none;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is) {
		return 72000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		releaseButton(item, player);
		return super.onDroppedByPlayer(item, player);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer pl) {
		if (!pl.isUsingItem())
			if (!pl.isSneaking()) {
				if (!isPressed(is)) {
					onPress(w, is);
					pl.playSound("random.click", 0.2F, 1.2F);
				}
				is.setItemDamage(is.getItemDamage() | 2);
				pl.setItemInUse(is, getMaxItemUseDuration(is));
			} else
				return super.onItemRightClick(is, w, pl);
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer ep, int time) {
		releaseButton(is, ep);
	}

	public void onPress(World w, ItemStack is) {
		if (IaSBaseItemLocational.hasPos(is)) {
			final int x = IaSBaseItemLocational.getX(is), y = IaSBaseItemLocational.getY(is),
					z = IaSBaseItemLocational.getZ(is);
			final Block bl = w.getBlock(x, y, z);
			if (bl instanceof IIaSSignalReceiverBlock)
				((IIaSSignalReceiverBlock) bl).onSignalStart(w, x, y, z, null);
		}
	}

	public void onRelease(World w, ItemStack is) {
		if (IaSBaseItemLocational.hasPos(is)) {
			final int x = IaSBaseItemLocational.getX(is), y = IaSBaseItemLocational.getY(is),
					z = IaSBaseItemLocational.getZ(is);
			final Block bl = w.getBlock(x, y, z);
			if (bl instanceof IIaSSignalReceiverBlock)
				((IIaSSignalReceiverBlock) bl).onSignalStop(w, x, y, z, null);
		}
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int enigma, boolean held) {
		if (!held && isPressed(is))
			releaseButton(is, e);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		pressed = reg.registerIcon(getTextureName() + "Pressed");
	}

	public void releaseButton(ItemStack is, Entity e) {
		is.setItemDamage(is.getItemDamage() & (~2));
		e.playSound("random.click", 0.2F, 0.8F);
		if (e instanceof EntityPlayer)
			((EntityPlayer) e).clearItemInUse();
		onRelease(e.worldObj, is);
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
