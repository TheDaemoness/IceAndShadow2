package iceandshadow2.nyx.blocks;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class NyxBlockSilkBerryPod extends BlockCocoa implements IIaSModName {

	protected IIcon[] icons;
	
	public NyxBlockSilkBerryPod(String par1) {
		super();
		this.setLightLevel(0.2F);
		this.setResistance(0.5F);
		this.setHardness(0.1F);
		this.setStepSound(soundTypeCloth);
		this.setBlockName("nyx"+par1);
		this.setBlockTextureName(this.getTexName());
	}
	
	public final Block register() {
		IaSRegistration.register(this);
		return this;
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		if(par5Random.nextBoolean())
			super.updateTick(par1World, par2, par3, par4, par5Random);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
        this.icons = new IIcon[3];
        for(int i = 0; i < 3; ++i)
        	this.icons[i] = reg.registerIcon(this.getTexName()+(i+1));
		this.blockIcon = this.icons[2];
	}

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return this.blockIcon;
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getCocoaIcon(int par1)
    {
        if (par1 < 0 || par1 >= this.icons.length)
            par1 = this.icons.length - 1;

        return this.icons[par1];
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int p_149690_5_, int fortune)
    {
        ArrayList<ItemStack> dropped = super.getDrops(world, x, y, z, p_149690_5_, fortune);
        int j1 = func_149987_c(p_149690_5_);
        byte b0 = 1;

        if (j1 >= 2)
            b0 = 3;

        for (int k1 = 0; k1 < b0; ++k1)
            dropped.add(new ItemStack(NyxItems.silkBerries, 1));
        return dropped;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        int l = getDirection(par1World.getBlockMetadata(par2, par3, par4));
        par2 += Direction.offsetX[l];
        par4 += Direction.offsetZ[l];
        Block i1 = par1World.getBlock(par2, par3, par4);
        return i1 == NyxBlocks.infestLog;
    }

    @Override
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_,
			int p_149694_4_) {
		return NyxItems.silkBerries;
	}

	/**
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return 0;
    }

	
	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+getModName();
	}


	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}
}
