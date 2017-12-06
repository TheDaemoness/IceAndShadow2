package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxItemAlabasterShard extends NyxItemAlabaster {
	
	public NyxItemAlabasterShard(String texName) {
		super(texName);
		setMaxStackSize(16);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(catalyst.getItem() != this)
			return 0;
		if(target.getItem() == Items.bone)
			return 210; //Smoulder it.
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		if(catalyst.getItem() == this) {
			catalyst.stackSize -= 1;
			target.stackSize -= 1;
			if(target.getItem() == Items.bone)
				retval.add(new ItemStack(NyxItems.boneSanctified));
		}
		return retval;
	}

}
