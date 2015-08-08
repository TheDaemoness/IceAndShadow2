package iceandshadow2.nyx.forge;

import iceandshadow2.ias.items.tools.IaSTools;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class NyxArmorHandler {

	@SubscribeEvent
	public void dmg(LivingHurtEvent e) {
		if(e.entity.worldObj.isRemote)
			return;
		EntityLivingBase elb = e.entityLiving;
		if(elb != null && e.source.isMagicDamage()) {
			if(e.source.isDamageAbsolute())
				return;
			if(elb.getEquipmentInSlot(1) != null &&
					elb.getEquipmentInSlot(1).getItem() == IaSTools.armorSpiderSilk[3]) {
				e.ammount -= 1;
				elb.getEquipmentInSlot(1).damageItem(1, elb);
			}
			if(elb.getEquipmentInSlot(2) != null &&
					elb.getEquipmentInSlot(2).getItem() == IaSTools.armorSpiderSilk[2]) {
				e.ammount -= 3;
				elb.getEquipmentInSlot(2).damageItem(3, elb);
			}
			if(elb.getEquipmentInSlot(3) != null &&
					elb.getEquipmentInSlot(3).getItem() == IaSTools.armorSpiderSilk[1]) {
				e.ammount -= 2;
				elb.getEquipmentInSlot(3).damageItem(2, elb);
			}
			if(elb.getEquipmentInSlot(4) != null &&
					elb.getEquipmentInSlot(4).getItem() == IaSTools.armorSpiderSilk[0]) {
				e.ammount -= 1;
				//Boots deliberately do not get damaged.
			}
			e.ammount = Math.max(1, e.ammount);
		}
	}
}
