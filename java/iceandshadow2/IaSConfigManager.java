package iceandshadow2;

import java.io.File;

public class IaSConfigManager {
	
	private File config;
	private boolean needs_write;
	private int exp_maj, exp_min;

	public IaSConfigManager(File cfgFile, int maj, int min) {
		config = cfgFile;
		needs_write = !cfgFile.exists();
	}
	
	public boolean needsWrite() {
		return needs_write;
	}
	
	public void read() {
		
	}
	
	public void write() {
		
	}

}
