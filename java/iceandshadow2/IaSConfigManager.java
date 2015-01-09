package iceandshadow2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

public class IaSConfigManager {

	private File config;
	private boolean needs_write;
	private int exp_maj, exp_min;

	public IaSConfigManager(File cfgFile, int maj, int min) {
		config = cfgFile;
		needs_write = !cfgFile.exists();
		if(!needs_write)
			needs_write = read();
		if(needs_write)
			write();
	}

	public boolean needsWrite() {
		return needs_write;
	}

	public boolean read() {
		Scanner lis;
		try {
			lis = new Scanner(config);
		} catch (FileNotFoundException e) {
			IceAndShadow2.getLogger().warn("Could not open config file for reading");
			e.printStackTrace();
			return true;
		}
		if(!lis.hasNextLine()) {
			IceAndShadow2.getLogger().error("Config file is empty!");
			lis.close();
			return true;
		}
		String config_ver[] = lis.nextLine().split("\\s+", 3);
		if(config_ver.length != 3) {
			IceAndShadow2.getLogger().error("Config file doesn't start with verion info (will overwrite)");
			lis.close();
			return true;
		}
		if(!config_ver[0].contentEquals("version")) {
			IceAndShadow2.getLogger().error("Config file doesn't start with verion info (will overwrite)");
			lis.close();
			return true;
		}
		if(Integer.parseInt(config_ver[1]) != exp_maj) {
			IceAndShadow2.getLogger().error("Incompatible configuration file version (will overwrite)");
			lis.close();
			return true;
		}
		while(lis.hasNextLine()) {
			String orig = lis.nextLine();
			String data[] = orig.split("\\s+", 3);
			if(data.length < 1) {
				IceAndShadow2.getLogger().error("Invalid number of space-separated fields (minimum 2): " + orig);
				continue;
			}
			if(data[0].charAt(0) == '#')
				continue;
			if(data.length < 2) {
				IceAndShadow2.getLogger().error("Invalid number of space-separated fields (minimum 2): " + orig);
				continue;
			}
			Field f;
			try {
				f = IaSFlags.class.getField(data[1]);
			} catch (NoSuchFieldException e) {
				IceAndShadow2.getLogger().error("No such config field: " + data[1]);
				continue;
			} catch (SecurityException e) {
				continue;
			}
			Object typecheck;
			try {
				typecheck = f.get(null);
			} catch (IllegalArgumentException e) {
				IceAndShadow2.getLogger().error("No such config field: " + data[1]);
				continue;
			} catch (IllegalAccessException e) {
				IceAndShadow2.getLogger().error("No such config field: " + data[1]);
				continue;
			}
			try {
				if(typecheck instanceof Boolean) {
					if(data.length != 2) {
						IceAndShadow2.getLogger().error("Invalid number of space-separated fields (expected 2): " + orig);
						continue;
					}
					if(data[0].contentEquals("enable"))
						f.set(null, true);
					else if(data[0].contentEquals("disable"))
						f.set(null, false);
					else
						IceAndShadow2.getLogger().error("Invalid prefix (expected 'enable' or 'disable): " + data[2]);
					continue;
				}
				if(data.length != 3) {
					IceAndShadow2.getLogger().error("Invalid number of space-separated fields (expected 3): " + orig);
					continue;
				}
				if(typecheck instanceof Integer) {
					if(!data[0].contentEquals("set-int"))
						IceAndShadow2.getLogger().warn("Ambiguous prefix (expected 'set-int'): " + data[0]);
					try {
						f.set(null, Integer.parseInt(data[2]));
					} catch (NumberFormatException e) {
						IceAndShadow2.getLogger().error("Malformed 32-bit integer: " + data[2]);
						continue;
					}
				}
				if(typecheck instanceof Short) {
					if(!data[0].contentEquals("set-byte"))
						IceAndShadow2.getLogger().warn("Ambiguous prefix (expected 'set-byte'): " + data[0]);
					try {
						short s = Short.parseShort(data[2]);
						if(s > 255) {
							IceAndShadow2.getLogger().error("Byte value > 255: " + data[2]);
							continue;
						}
						f.set(null, s);
					} catch (NumberFormatException e) {
						IceAndShadow2.getLogger().error("Malformed 8-bit integer: " + data[2]);
						continue;
					}
				}
				if(typecheck instanceof String) {
					if(!data[0].contentEquals("set-string"))
						IceAndShadow2.getLogger().warn("Ambiguous prefix (expected 'set-string'): " + data[0]);
					f.set(null, data[2]);
				}
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
		}
		lis.close();
		return Integer.parseInt(config_ver[2]) < exp_min;
	}

	public void write() {
		PrintWriter ecris;
		try {
			ecris = new PrintWriter(config);
		} catch (FileNotFoundException e) {
			IceAndShadow2.getLogger().warn("Could not open config file for writing");
			e.printStackTrace();
			return;
		}
		ecris.println("version " + exp_maj + ' ' + exp_min);
		ecris.println("#https://github.com/TheRabbitologist/IceAndShadow2/wiki/Configuration-File-Settings");
		ecris.println();
		for(Field f : IaSFlags.class.getFields()) {
			Object data;
			try {
				data = f.get(null);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
			if(data instanceof Integer)
				ecris.println("set-int " + f.getName() + ' ' + data);
			else if(data instanceof Short)
				ecris.println("set-byte " + f.getName() + ' ' + data);
			else if(data instanceof Boolean) {
				if(((Boolean)data).booleanValue())
					ecris.println("enable " + f.getName());
				else
					ecris.println("disable " + f.getName());
			}
			else if (data instanceof String)
				ecris.println("set-string " + f.getName() + ' ' + data);
		}
		ecris.close();
	}

}
