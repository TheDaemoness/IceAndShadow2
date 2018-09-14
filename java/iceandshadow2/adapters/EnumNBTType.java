package iceandshadow2.adapters;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;

/**
 * Physically painful boilerplace for the NBT system.
 */
public enum EnumNBTType {
	// WARNING: Order-sensitive!
	END {
		@Override
		public Boolean get(NBTTagCompound tags, String key) {
			return tags.getTag(key) instanceof NBTTagEnd;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			return get(tags, key);
		}
	},
	BYTE {
		@Override
		public Byte get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getByte(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Byte value = get(tags, key);
			if (value != null)
				return value.byteValue() != 0;
			return false;
		}
	},
	SHORT {
		@Override
		public Short get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getShort(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Short value = get(tags, key);
			if (value != null)
				return value.shortValue() != 0;
			return false;
		}
	},
	INT {
		@Override
		public Integer get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getInteger(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Integer value = get(tags, key);
			if (value != null)
				return value.intValue() != 0;
			return false;
		}
	},
	LONG {
		@Override
		public Long get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getLong(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Long value = get(tags, key);
			if (value != null)
				return value.longValue() != 0;
			return false;
		}
	},
	FLOAT {
		@Override
		public Float get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getFloat(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Float value = get(tags, key);
			if (value != null)
				return !value.isNaN();
			return false;
		}
	},
	DOUBLE {
		@Override
		public Double get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getDouble(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final Double value = get(tags, key);
			if (value != null)
				return !value.isNaN();
			return false;
		}
	},
	BYTEARRAY {
		@Override
		public byte[] get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getByteArray(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final byte[] value = get(tags, key);
			if (value != null)
				return value.length > 0;
			return false;
		}
	},
	STRING {
		@Override
		public String get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getString(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final String value = get(tags, key);
			if (value != null)
				return !value.isEmpty();
			return false;
		}
	},
	TAGLIST {
		@Override
		public NBTTagList get(NBTTagCompound tags, String key) {
			final NBTBase tag = tags.getTag(key);
			if (tag instanceof NBTTagList)
				return (NBTTagList) tag;
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final NBTTagList value = get(tags, key);
			if (value != null)
				return value.tagCount() > 0;
			return false;
		}
	},
	COMPOUND {
		@Override
		public NBTTagCompound get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getCompoundTag(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final NBTTagCompound value = get(tags, key);
			if (value != null)
				return !value.hasNoTags();
			return false;
		}
	},
	INTARRAY {
		@Override
		public int[] get(NBTTagCompound tags, String key) {
			if (has(tags, key))
				return tags.getIntArray(key);
			return null;
		}

		@Override
		public boolean getBool(NBTTagCompound tags, String key) {
			final int[] value = get(tags, key);
			if (value != null)
				return value.length > 0;
			return false;
		}
	};

	public static EnumNBTType getListType(NBTTagList tags) {
		return values()[tags.func_150303_d()];
	}

	public static EnumNBTType getType(NBTBase tag) {
		return values()[tag.getId()];
	}

	public static EnumNBTType getType(NBTTagCompound tags, String key) {
		return values()[tags.func_150299_b(key)];
	}

	public static NBTBase make(Object value) {
		if (value instanceof NBTBase)
			return ((NBTBase) value).copy();
		if (value == null)
			return new NBTTagEnd();
		if (value instanceof Byte)
			return new NBTTagByte((Byte) value);
		if (value instanceof Boolean)
			return new NBTTagByte((byte) (((Boolean) value).booleanValue() ? 1 : 0));
		if (value instanceof Short)
			return new NBTTagShort((Short) value);
		if (value instanceof Integer)
			return new NBTTagInt((Integer) value);
		if (value instanceof Long)
			return new NBTTagLong((Long) value);
		if (value instanceof Float)
			return new NBTTagFloat((Float) value);
		if (value instanceof Double)
			return new NBTTagDouble((Double) value);
		if (value instanceof byte[])
			return new NBTTagByteArray((byte[]) value);
		if (value instanceof int[])
			return new NBTTagIntArray((int[]) value);
		if (value instanceof String)
			return new NBTTagString((String) value);
		if (value instanceof ItemStack) {
			NBTTagCompound compound = new NBTTagCompound();
			compound = ((ItemStack) value).writeToNBT(compound);
			return compound;
		}
		if (value instanceof Entity) {
			final NBTTagCompound compound = new NBTTagCompound();
			((Entity) value).writeToNBT(compound);
			return compound;
		}
		if (value instanceof TileEntity) {
			final NBTTagCompound compound = new NBTTagCompound();
			((TileEntity) value).writeToNBT(compound);
			return compound;
		}
		return null;
	}

	public static boolean set(NBTTagCompound tags, String key, Object value) {
		final NBTBase tag = make(value);
		if (tag != null) {
			tags.setTag(key, tag);
			return true;
		}
		return false;
	}

	public abstract <T> T get(NBTTagCompound tags, String key);

	// public abstract <T> T get(NBTTagList tags, int index);
	public boolean getBool(NBTTagCompound tags, String key) {
		return get(tags, key) != null;
	}

	public byte getId() {
		return (byte) ordinal();
	}

	public NBTTagList getList(NBTTagCompound tags, String key) {
		return tags.getTagList(key, getId());
	}

	public NBTBase getTag(NBTTagCompound tags, String key) {
		if (tags.hasKey(key, getId()))
			return tags.getTag(key);
		return null;
	}

	public boolean has(NBTTagCompound tags, String key) {
		if (tags == null || key == null || key.isEmpty())
			return false;
		return tags.hasKey(key, getId());
	}
}
