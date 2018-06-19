// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   Customizer.java

package net.sf.nachocalendar.customizer;

import java.util.Set;

public interface Customizer {

	public abstract int getInteger(String s);

	public abstract boolean getBoolean(String s);

	public abstract String getString(String s);

	public abstract long getLong(String s);

	public abstract float getFloat(String s);

	public abstract double getDouble(String s);

	public abstract Set keySet();
}
