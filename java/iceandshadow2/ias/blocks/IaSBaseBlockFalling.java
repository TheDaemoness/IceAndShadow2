package iceandshadow2.ias.blocks;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IIaSModName;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class IaSBaseBlockFalling extends BlockFalling implements IIaSModName {
	public final EnumIaSModule MODULE;
	
	public IaSBaseBlockFalling(EnumIaSModule mod, String texName, Material mat) {
		super(mat);
		this.setBlockName(mod.prefix+texName);
		this.setBlockTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		MODULE = mod;
	}
	
	public final IaSBaseBlockFalling register() {
		IaSRegistration.register(this);
		return this;
	}
	
	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+MODULE.prefix+getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}
	
}