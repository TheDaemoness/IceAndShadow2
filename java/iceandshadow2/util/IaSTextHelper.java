package iceandshadow2.util;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class IaSTextHelper {
	public static NBTTagList makeBookPages(String input) {
		final NBTTagList pages = new NBTTagList();
		final String[] words = input.split("\\s+");
		while(!input.isEmpty()) {
			final int line = 1;
			final NBTTagString page = new NBTTagString();
			while(line <= 14) {

			}
		}
		return null;
	}
}
