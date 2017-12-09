package iceandshadow2.nyx.tileentities;

import java.util.Map;
import java.util.TreeMap;

import iceandshadow2.ias.IaSTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class NyxTeExaminationTable extends IaSTileEntity {
	public Map<String, Integer> knowledge;

	public NyxTeExaminationTable() {
		knowledge = new TreeMap<String, Integer>();
	}

	@Override
	public void readFromNBT(NBTTagCompound in) {
		super.readFromNBT(in);
		if (in.hasKey("nyxKnowledge")) {
			final NBTTagCompound nbt = in.getCompoundTag("nyxKnowledge");
			for (final Object str : nbt.func_150296_c()) {
				final String key = (String) str;
				knowledge.put(key, nbt.getInteger(key));
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound in) {
		super.writeToNBT(in);
		final NBTTagCompound nbt = in.getCompoundTag("nyxKnowledge");
		for (final String keys : knowledge.keySet())
			nbt.setInteger(keys, knowledge.get(keys));
		in.setTag("nyxKnowledge", nbt);
	}
}
