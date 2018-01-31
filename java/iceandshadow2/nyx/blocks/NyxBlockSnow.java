package iceandshadow2.nyx.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockFalling;
import iceandshadow2.ias.util.IaSBlockHelper;

public class NyxBlockSnow extends IaSBaseBlockFalling {

	public NyxBlockSnow(String texName) {
		super(EnumIaSModule.NYX, texName, Material.craftedSnow);
		setHardness(0.3F);
		this.setHarvestLevel("shovel", 0);
		setStepSound(Block.soundTypeSnow);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.snowball, 4), new ItemStack(this));
		setLightOpacity(5);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side != ForgeDirection.DOWN;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.FROZEN;
	}

	// Apparently this stops updates during chunk generation, which should speed
	// things up.
	@Override
	public boolean func_149698_L() {
		return false;
	}

	@Override
	public void onFallenUpon(World w, int x, int y, int z, Entity e,
			float f) {
		e.fallDistance /= 2f;
		w.scheduleBlockUpdate(x, y, z, this, tickRate(w));
		w.getBlock(x, y-1, z).onEntityWalking(w, x, y-1, z, e);
	}

	@Override
	public void onEntityWalking(World w, int x, int y, int z,
			Entity e) {
		if(e instanceof EntityMob) {
		} else {
			w.scheduleBlockUpdate(x, y, z, this, tickRate(w));
			final Block beneath = w.getBlock(x, y-1, z);
			if(beneath instanceof NyxBlockSnow) {
				w.scheduleBlockUpdate(x, y-1, z, this, tickRate(w));
			}
		}
		super.onEntityWalking(w, x, y, z, e);
	}

    @Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block bl)
    {
    	if(!IaSBlockHelper.isAir(bl)) {
			p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, tickRate(p_149695_1_));
		}
    }

}
