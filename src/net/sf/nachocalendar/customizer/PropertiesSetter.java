// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   PropertiesSetter.java

package net.sf.nachocalendar.customizer;

import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;

public interface PropertiesSetter {

	public abstract void customize(DateField datefield);

	public abstract void customize(CalendarPanel calendarpanel);

	public abstract void customize(DatePanel datepanel);
}
