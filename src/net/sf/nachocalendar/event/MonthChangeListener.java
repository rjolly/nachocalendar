// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MonthChangeListener.java

package net.sf.nachocalendar.event;

import java.util.EventListener;

// Referenced classes of package net.sf.nachocalendar.event:
//			MonthChangeEvent

public interface MonthChangeListener
	extends EventListener {

	public abstract void monthIncreased(MonthChangeEvent monthchangeevent);

	public abstract void monthDecreased(MonthChangeEvent monthchangeevent);
}
