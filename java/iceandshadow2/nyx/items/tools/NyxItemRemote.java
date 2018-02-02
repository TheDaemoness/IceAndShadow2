package iceandshadow2.nyx.items.tools;

import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.tools.IaSBaseItemEquippable;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IntBits;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemRemote extends IaSBaseItemEquippable implements IIaSGlowing {

	public static boolean isPressed(ItemStack is) {
		return IntBits.areAllSet(is.getItemDamage(), 2);
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon pressed;
	
	public NyxItemRemote(String par1) {
		super(EnumIaSModule.NYX, par1);
	}
	
	public void onPress(ItemStack is) {}
	public void onRelease(ItemStack is) {}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer pl) {
		if(!pl.isUsingItem()) {
			if(!isPressed(is)) {
				onPress(is);
				pl.playSound("random.click", 0.2F, 1.2F);
			}
			is.setItemDamage(is.getItemDamage() | 2);
			pl.setItemInUse(is, getMaxItemUseDuration(is));
		}
		return is;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.none;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is) {
		return 72000;
	}
	
	public void releaseButton(ItemStack is, Entity e) {
		is.setItemDamage(is.getItemDamage() & (~2));
		e.playSound("random.click", 0.2F, 0.8F);
		if(e instanceof EntityPlayer)
			((EntityPlayer)e).clearItemInUse();
		onRelease(is);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer ep, int time) {
		releaseButton(is, ep);
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int enigma,
			boolean held) {
		if(!held && isPressed(is))
			releaseButton(is, e);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
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
	public IIcon getIcon(ItemStack stack, int pass) {
		if(isPressed(stack))
			return this.pressed;
		return this.itemIcon;
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		releaseButton(item, player);
		return super.onDroppedByPlayer(item, player);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.pressed = reg.registerIcon(getTextureName() + "Pressed");
	}
	
	
}
