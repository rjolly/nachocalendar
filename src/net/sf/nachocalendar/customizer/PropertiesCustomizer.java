// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertiesCustomizer.java

package net.sf.nachocalendar.customizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

// Referenced classes of package net.sf.nachocalendar.customizer:
//			Customizer, PropertiesConverter

public class PropertiesCustomizer
	implements Customizer {

	private Properties properties;

	public PropertiesCustomizer(InputStream config) throws IOException {
		properties = new Properties();
		properties.load(config);
	}

	public int getInteger(String key) {
		return PropertiesConverter.getInteger(properties.getProperty(key));
	}

	public boolean getBoolean(String key) {
		return PropertiesConverter.getBoolean(properties.getProperty(key));
	}

	public String getString(String key) {
		return properties.getProperty(key);
	}

	public long getLong(String key) {
		return PropertiesConverter.getLong(properties.getProperty(key));
	}

	public float getFloat(String key) {
		return PropertiesConverter.getFloat(properties.getProperty(key));
	}

	public double getDouble(String key) {
		return PropertiesConverter.getDouble(properties.getProperty(key));
	}

	public Set keySet() {
		return properties.keySet();
	}
}
