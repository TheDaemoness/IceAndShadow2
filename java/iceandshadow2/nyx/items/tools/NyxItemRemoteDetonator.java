package iceandshadow2.nyx.items.tools;

import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IntBits;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
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

public class NyxItemRemoteDetonator extends NyxItemRemote {

	public NyxItemRemoteDetonator(String par1) {
		super(par1);
	}

	@Override
	public boolean canAcceptAmmo(ItemStack ammo) {
		return false;
	}
	
	//NOTE: Do not set on press/on release, since the grenades detect an active remote detonator on their own.
}
