package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IaSBlockDirectional extends IaSBaseBlockSingle {
	
	@SideOnly(Side.CLIENT)
	IIcon iconSide;
	
	public IaSBlockDirectional(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getRenderType() {
        return 31;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
    	this.blockIcon = reg.registerIcon(this.getTexName()+"Top");
    	this.iconSide = reg.registerIcon(this.getTexName()+"Side");
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
            	align = 0x8;
                break;
            case 4:
            case 5:
            	align = 0x4;
        }

        return align;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
    {
		
        //Connector
        if((meta & 0xB) == 0xB) {
        	return this.blockIcon;	
        }
        
    	//East-West
        else if((meta & 0x4) == 0x4) { 
        	if(side == 4 || side == 5)
        		return this.blockIcon;
        	else if(side == 0 || side == 1)
        		return iconSide;
        	else
        		return iconSide;
        }
        
        //North-South
        else if((meta & 0x8) == 0x8) {
        	if(side == 2 || side == 3)
        		return this.blockIcon;
        	else if(side == 0 || side == 1)
        		return iconSide;
        	else
        		return iconSide;
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
