package iceandshadow2.nyx.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.NyxTeleporter;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSEntityHelper;
import iceandshadow2.util.IaSPlayerHelper;

public class NyxTeleportCrystal extends IaSBaseItemSingle {

	IIcon empty;
	
	public NyxTeleportCrystal(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(1);
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if((dmg & 4) == 4)
			return empty;
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		empty = reg.registerIcon(this.getTexName()+"Empty");
	}

	@Override
	public void onUpdate(ItemStack pile, World earth,
			Entity tree, int time, boolean boule) {
		if(tree.worldObj.isRemote)
			return;
		boolean active = true;
		if(tree.dimension != IaSFlags.dim_nyx_id) {
			active &= IaSEntityHelper.getTemperatureFloat(tree) <= 0.15;
			if((pile.getItemDamage() & 1) == 1)
				active &= IaSEntityHelper.getLight(tree) <= 6;
			else
				active &= IaSEntityHelper.getLight(tree) <= 5;
		}
		else {
			//active = IaSEntityHelper.getBlock(tree,0,-0.1,0) == NyxBlocks.cryingObsidian;
			active &= pile.getItemDamage() < 4;
		}
		if(!active & (pile.getItemDamage() & 1) == 1)
			pile.setItemDamage(pile.getItemDamage()-1);
		else if(active & (pile.getItemDamage() & 1) == 0)
			pile.setItemDamage(pile.getItemDamage()+1);
	}


	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		if((par1ItemStack.getItemDamage() & 1) == 1)
			return pass == 0;
		return false;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if(player.dimension != IaSFlags.dim_nyx_id && count < 50)
			player.addPotionEffect(new PotionEffect(Potion.blindness.id,Math.min(50-count,25),0));
		else if(count < 40 && player.dimension == IaSFlags.dim_nyx_id)
			player.addPotionEffect(new PotionEffect(Potion.blindness.id,Math.min(50-count,25),0));
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 140;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World wld,
			EntityPlayer pl) {
		if((is.getItemDamage() & 1) == 1) {
			if(pl instanceof EntityPlayerMP) {
				EntityPlayerMP plm = (EntityPlayerMP)pl;
				if((is.getItemDamage() & 2) == 0) {
					is.setItemDamage(is.getItemDamage()+2);
					//GIMME SEED!
				}
				if (pl.dimension != IaSFlags.dim_nyx_id) {
					plm.mcServer
					.getConfigurationManager()
					.transferPlayerToDimension(
							plm,
							IaSFlags.dim_nyx_id,
							new NyxTeleporter(
									plm.mcServer
									.worldServerForDimension(IaSFlags.dim_nyx_id)));
				} else {
					if((is.getItemDamage() & 4) == 0)
						is.setItemDamage(is.getItemDamage()+4);
					plm.mcServer.getConfigurationManager()
					.transferPlayerToDimension(
							plm,
							0,
							new NyxTeleporter(plm.mcServer
									.worldServerForDimension(0)));
				}
			}
		}
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order,
			EntityPlayer pwai) {
		if((heap.getItemDamage() & 4) == 4 && pwai.dimension == IaSFlags.dim_nyx_id) {
			IaSPlayerHelper.messagePlayer(pwai, "You find strange thoughts coming to your mind. Something about getting power from etherium cores made from merged etherium dust...");
			pwai.setItemInUse(heap,72000);
			return heap;
		}
		if((heap.getItemDamage() & 1) == 0) {
			if(pwai.dimension == IaSFlags.dim_nyx_id)
				IaSPlayerHelper.messagePlayer(pwai, "You find strange thoughts coming to your mind. Something about needing to be standing on crying obsidian...");
			else
				IaSPlayerHelper.messagePlayer(pwai, "The crystal barely responds. It seems to prefer cold and dark places.");
			pwai.setItemInUse(heap,72000);
			return heap;
		}
		pwai.setItemInUse(heap,
				this.getMaxItemUseDuration(heap));
		pwai.addPotionEffect(new PotionEffect(Potion.confusion.id,200,0));
		return heap;
	}

	/*

	 */
}
