package iceandshadow2.nyx.tileentities;

import java.util.Map;
import java.util.TreeMap;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

public class NyxTeExaminationTable extends IaSTileEntity {
	public Map<String,Integer> knowledge;
	
	public NyxTeExaminationTable() {
		knowledge = new TreeMap<String,Integer>();
	}

	@Override
	public void writeToNBT(NBTTagCompound in) {
		super.writeToNBT(in);
		NBTTagCompound nbt = in.getCompoundTag("nyxKnowledge");
		for(String keys : knowledge.keySet())
			nbt.setInteger(keys, knowledge.get(keys));
		in.setTag("nyxKnowledge", nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound in) {
		super.readFromNBT(in);
		if(in.hasKey("nyxKnowledge")) {
			NBTTagCompound nbt = in.getCompoundTag("nyxKnowledge");
			for(Object str : nbt.func_150296_c()) {
				String key = (String)str;
				knowledge.put(key, nbt.getInteger(key));
			}
		}
	}
}
