package iceandshadow2.ias.items.tools;

import com.google.common.collect.Multimap;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IaSItemAxe extends ItemAxe implements IIaSModName {

	public IaSItemAxe() {
		super(ToolMaterial.IRON);
		this.setUnlocalizedName("iasToolAxe");
	}

	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase user,
			EntityLivingBase target) {
		//TODO: Damage control.
		return true;
	}
	
	/**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        //multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }

	@Override
	public boolean onBlockDestroyed(ItemStack is, World w,
			Block bl, int x, int y,
			int z, EntityLivingBase user) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		m.onHarvest(is, user, w, x, y, z);
		return true;
	}

	@Override
	public int getItemEnchantability() {
		return 16;
	}

	@Override
	public String getToolMaterialName() {
		return "Wabbagoogies";
	}

	@Override
	public boolean getIsRepairable(ItemStack is, ItemStack two) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return false;
		return m.isRepairable(is, two);
	}

	@Override
	public int getHarvestLevel(ItemStack is, String toolClass) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getHarvestLevel(is, toolClass);
	}



	@Override
	public float getDigSpeed(ItemStack is, Block block, int meta) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getHarvestSpeed(is, block, meta, false);
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	@Deprecated
	public String getTexName() {
		return null;
	}
	

	@Override
	public String getUnlocalizedName(ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return null;
		return m.getUnlocalizedName(is);
	}

	@Override
	public IIcon getIcon(ItemStack is, int pass) {
		if(pass != 0)
			return null;
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return null;
		return m.getIcon(is);
	}

	@Override
	public int getMaxDamage(ItemStack is) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		if(m == null)
			return 0;
		return m.getDurability(is, false);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.IAS;
	}

}