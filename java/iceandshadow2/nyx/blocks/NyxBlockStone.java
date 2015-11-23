package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSBlockThawable;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.Block;
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
import cpw.mods.fml.common.registry.GameRegistry;

public class NyxBlockStone extends IaSBaseBlockSingle implements IIaSBlockThawable {
	public static final float HARDNESS = 2.0F;
	public static final float RESISTANCE = 9.0F;

	public static void doDamage(World theWorld, int x, int y, int z,
			Entity theEntity, int dmg) {
		if (!(theEntity instanceof EntityMob)) {
			if (theEntity instanceof EntityLivingBase) {
				if (((EntityLivingBase) theEntity).getEquipmentInSlot(1) != null) {
					final Item it = ((EntityLivingBase) theEntity)
							.getEquipmentInSlot(1).getItem();
					if (it instanceof ItemArmor) {
						if (((ItemArmor) it).getArmorMaterial()
								.getDamageReductionAmount(3) == 1)
							theEntity.attackEntityFrom(
									IaSDamageSources.dmgStone, dmg / 2 + 1);
						if(((EntityLivingBase) theEntity).getEquipmentInSlot(1).attemptDamageItem(1, theWorld.rand))
							((EntityLivingBase) theEntity).setCurrentItemOrArmor(1, null);
					}
					return;
				}
			}
			theEntity.attackEntityFrom(IaSDamageSources.dmgStone, dmg);
		}
	}

	public NyxBlockStone(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(NyxBlockStone.HARDNESS);
		setHardness(NyxBlockStone.RESISTANCE);
		this.setHarvestLevel("pickaxe", 0);
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.cobblestone),
				new ItemStack(this));
		GameRegistry.addSmelting(this, new ItemStack(Blocks.stone), 0);
	}

	public boolean isGenMineableReplaceable(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4,
			EntityPlayer par5EntityPlayer) {
		if (par5EntityPlayer.getCurrentEquippedItem() == null
				&& par5EntityPlayer.worldObj.difficultySetting
				.getDifficultyId() > 0)
			par5EntityPlayer.attackEntityFrom(IaSDamageSources.dmgStone,
					par5EntityPlayer.worldObj.difficultySetting
					.getDifficultyId());
	}

	@Override
	public void onEntityWalking(World theWorld, int x, int y, int z,
			Entity theEntity) {
		NyxBlockStone.doDamage(theWorld, x, y, z, theEntity,
				theEntity.worldObj.difficultySetting.getDifficultyId() + 2);
	}

	@Override
	public void onFallenUpon(World woild, int x, int y, int z,
			Entity theEntity, float height) {
		super.onFallenUpon(woild, x, y, z, theEntity, height);

		int dmg = 2 * theEntity.worldObj.difficultySetting.getDifficultyId();
		if (dmg == 0)
			dmg = 1;
		NyxBlockStone.doDamage(woild, x, y, z, theEntity, dmg);
	}

	@Override
	public Block onThaw(World w, int x, int y, int z) {
		return Blocks.stone;
	}
}
