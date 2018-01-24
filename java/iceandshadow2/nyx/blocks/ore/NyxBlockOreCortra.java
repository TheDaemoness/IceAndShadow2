package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockSalt;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreCortra extends NyxBlockSalt {

	public NyxBlockOreCortra(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		setHardness(20.0F);
		setLuminescence(0.6F);
		setLightColor(0.0F, 0.75F, 1.0F);
		setResistance(10.0F);
		GameRegistry.addSmelting(this, new ItemStack(NyxItems.cortra, 1), 4);
	}
	
	@Override
	public int getSubtypeCount() {
		return 1;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		final int e = world.rand.nextInt(2 + fortune);
		is.add(new ItemStack(NyxItems.cortra));
		is.add(new ItemStack(Items.redstone, 2));
		for (int i = 0; i < e; ++i)
			is.add(world.rand.nextInt(2 + fortune)>1?new ItemStack(NyxItems.cortra):new ItemStack(Items.redstone));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 3;
	}
	
	public String getUnlocalizedName(int val) {
		return super.getUnlocalizedName();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[getSubtypeCount()];
		for (byte i = 0; i < getSubtypeCount(); ++i)
			icons[i] = reg.registerIcon(getTextureName());
	}
}
