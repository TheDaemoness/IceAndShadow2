package iceandshadow2.ias.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSFakeBlock;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IaSBlockDirectional extends IaSBaseBlockSingle {
	
	@SideOnly(Side.CLIENT)
	IIcon iconSide, iconSideRotated;
	
	public IaSBlockDirectional(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
    	this.blockIcon = reg.registerIcon(this.getTexName()+"Top");
    	this.iconSide = reg.registerIcon(this.getTexName()+"Side");
    	this.iconSideRotated = reg.registerIcon(this.getTexName()+"SideR");
    }
	
	@Override
	protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(this, 1, 0);
    }
	
	@Override
	public int onBlockPlaced(World par1World, int x, int y, int z, int face, float par6, float par7, float par8, int par9) {
        byte align = 0;

        switch (face)
        {
            case 0:
            case 1:
            	align = 0;
                break;
            case 2:
            case 3:
            	align = 0x2;
                break;
            case 4:
            case 5:
            	align = 0x1;
        }

        return align;
    }

	@Override
	public IIcon getIcon(int side, int meta)
    {
		
        //Connector
        if((meta & 0x3) == 0x3) {
        	return this.blockIcon;	
        }
        
    	//East-West
        else if((meta & 0x1) == 0x1) { 
        	if(side == 4 || side == 5)
        		return this.blockIcon;
        	else if(side == 0 || side == 1)
        		return iconSideRotated;
        	else
        		return iconSideRotated;
        }
        
        //North-South
        else if((meta & 0x2) == 0x2) {
        	if(side == 2 || side == 3)
        		return this.blockIcon;
        	else if(side == 0 || side == 1)
        		return iconSide;
        	else
        		return iconSideRotated;
        }
        //Up-down
        else {
        	if(side == 0 || side == 1)
        		return this.blockIcon;
        	else
        		return iconSide;
        }
    }
}
