package iceandshadow2.nyx.blocks;

import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class NyxBlockPermafrost extends IaSBaseBlockSingle {
	
	public NyxBlockPermafrost(String id) {
		super(EnumIaSModule.NYX, id, Material.packedIce);
    	setHardness(3.0F);
    	setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
	}
	
	private IIcon iconTop, iconSide;

	@Override
	public void registerBlockIcons(IIconRegister reg) {
    	this.iconTop = reg.registerIcon(this.getTexName()+"Top");
    	this.iconSide = reg.registerIcon(this.getTexName()+"Side");
	}

	@Override
	public IIcon getIcon(int side, int meta)
    {
        if(side == 0 || side == 1)
        	return iconTop;
        else
        	return iconSide;
    }
}
