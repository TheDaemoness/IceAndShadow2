package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class NyxItemSeedObsidian extends IaSBaseItemSingle {

	public NyxItemSeedObsidian(String texName) {
		super(EnumIaSModule.NYX, texName);
		setMaxStackSize(16);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.rare;
	}

	@Override
	public boolean onItemUse(ItemStack staq, EntityPlayer playuh, World wurld, int ehx, int uay, int zee, int metuh,
			float pex, float pay, float pez) {
		boolean trans = true;
		for (int xit = -2; xit <= 2; ++xit)
			for (int zit = -2; zit <= 2; ++zit)
				if (wurld.getBlock(ehx + xit, uay, zee + zit) != NyxBlocks.stone)
					trans = false;
		if (trans) {
			for (int xit = -2; xit <= 2; ++xit)
				for (int zit = -2; zit <= 2; ++zit) {
					if (Math.abs(xit) == 2 || Math.abs(zit) == 2)
						wurld.setBlock(ehx + xit, uay, zee + zit, Blocks.obsidian);
					else
						wurld.setBlock(ehx + xit, uay, zee + zit, NyxBlocks.cryingObsidian, 1, 0x2);

					if (xit == 0 && zit == 0)
						wurld.func_147480_a(ehx + xit, uay + 3, zee + zit, true);
					if (xit <= 1 && zit <= 1)
						wurld.func_147480_a(ehx + xit, uay + 2, zee + zit, true);
					wurld.func_147480_a(ehx + xit, uay + 1, zee + zit, true);
				}
			staq.stackSize -= 1;
			wurld.spawnEntityInWorld(new EntityLightningBolt(wurld, ehx, uay, zee));
			IaSPlayerHelper.messagePlayer(playuh, "You feel something bind your life force to the obsidian.");
			playuh.setSpawnChunk(new ChunkCoordinates(ehx, uay + 1, zee), true);
			if (playuh.getHealth() > 2.0F)
				playuh.attackEntityFrom(DamageSource.magic, 1);
			IaSPlayerHelper.messagePlayer(playuh,
					"You may be able to rebind it to other platforms, simply by crouching on the center block.");
			return true;
		} else
			IaSPlayerHelper.messagePlayer(playuh,
					"That seed needs a larger flat area of Nyxian stone to avoid being wasteful. How do I know that...?");
		return false;
	}
}
