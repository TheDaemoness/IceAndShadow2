package iceandshadow2.nyx.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxBlockFrozenBrick extends IaSBaseBlockSingle {
	public NyxBlockFrozenBrick(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setResistance(9.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setLuminescence(0.15F);
	}
}
