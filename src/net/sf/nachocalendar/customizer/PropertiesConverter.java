// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertiesConverter.java

package net.sf.nachocalendar.customizer;


public final class PropertiesConverter {

	private PropertiesConverter() {
	}

	public static int getInteger(String value) {
		int retorno = -1;
		if (value == null)
			return retorno;
		try {
			retorno = Integer.parseInt(value);
		}
		catch (NumberFormatException e) { }
		return retorno;
	}

	public static boolean getBoolean(String value) {
		if (value == null)
			return false;
		else
			return Boolean.valueOf(value).booleanValue();
	}

	public static long getLong(String value) {
		long retorno = -1L;
		if (value == null)
			return retorno;
		try {
			retorno = Long.parseLong(value);
		}
		catch (NumberFormatException e) { }
		return retorno;
	}

	public static float getFloat(String value) {
		float retorno = -1F;
		if (value == null)
			return retorno;
		try {
			retorno = Float.parseFloat(value);
		}
		catch (NumberFormatException e) { }
		return retorno;
	}

	public static double getDouble(String value) {
		double retorno = -1D;
		if (value == null)
			return retorno;
		try {
			retorno = Double.parseDouble(value);
		}
		catch (NumberFormatException e) { }
		return retorno;
	}
}
