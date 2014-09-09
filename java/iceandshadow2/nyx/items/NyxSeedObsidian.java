package iceandshadow2.nyx.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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

public class NyxSeedObsidian extends IaSBaseItemSingle {
	
	public NyxSeedObsidian(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(16);
	}

	@Override
	public boolean onItemUse(ItemStack staq, EntityPlayer playuh,
			World wurld, int ehx, int uay, int zee,
			int metuh, float pex, float pay,
			float pez) {
		boolean trans = true;
		for(int xit = -2; xit <= 2; ++xit) {
			for(int zit = -2; zit <= 2; ++zit) {
				if(wurld.getBlock(ehx+xit, uay, zee+zit) != NyxBlocks.stone)
					trans = false;
			}
		}
		if(trans) {
			for(int xit = -2; xit <= 2; ++xit) {
				for(int zit = -2; zit <= 2; ++zit)
					if(Math.abs(xit) == 2 || Math.abs(zit) == 2)
						wurld.setBlock(ehx+xit, uay, zee+zit, Blocks.obsidian);
					else
						wurld.setBlock(ehx+xit, uay, zee+zit, NyxBlocks.cryingObsidian, 1, 0x2);
			}
			staq.stackSize -= 1;
			return true;
			
		} else
			IaSPlayerHelper.messagePlayer(playuh, "That seed needs a larger flat area of Nyxian stone to avoid being wasteful. How do I know that...?");
		return false;
	}
}
