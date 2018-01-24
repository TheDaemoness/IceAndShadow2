package iceandshadow2.nyx.blocks.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlock;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockRail extends BlockRailBase implements IIaSModName, IIaSAspect {

	@SideOnly(Side.CLIENT)
	protected IIcon alt;
	protected boolean curvy;
	
	public NyxBlockRail(String name, boolean powered, boolean curvy) {
		super(powered);
		this.curvy = curvy && !powered;
		setBlockName(EnumIaSModule.NYX.prefix + name);
		this.setLightLevel(0.2f);
	}
	
	@Override
	public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z) {
		return curvy;
	}
	
	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z)
    {
		/*
		for(int i = 2; i <= 5; ++i) {
			final ForgeDirection dir = ForgeDirection.getOrientation(i);
			for(int yi = -1; yi <= 1; ++yi) {
				final int meta = world.getBlockMetadata(x+dir.offsetX, y+yi, z+dir.offsetZ);
				final Block bl = world.getBlock(x+dir.offsetX, y+yi, z+dir.offsetZ);
				if(bl instanceof BlockRailBase && meta >= 6) {
					return 0.5f;
				}
			}
		}
		*/
        return 0.7f;
    }

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}
	
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        super.registerBlockIcons(reg);
        if(curvy)
        	this.alt = reg.registerIcon(this.getTextureName() + "Curved");
        else if(this.isPowered())
        	this.alt = reg.registerIcon(this.getTextureName() + "On");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_2_ >= 6 ? this.alt : this.blockIcon;
    }

	public Block register() {
		IaSRegistration.register(this);
		return this;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NATIVE;
	}
}
