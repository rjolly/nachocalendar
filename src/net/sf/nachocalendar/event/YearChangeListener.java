// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   YearChangeListener.java

package net.sf.nachocalendar.event;

import java.util.EventListener;

// Referenced classes of package net.sf.nachocalendar.event:
//			YearChangeEvent

public interface YearChangeListener
	extends EventListener {

	public abstract void yearIncreased(YearChangeEvent yearchangeevent);

	public abstract void yearDecreased(YearChangeEvent yearchangeevent);
}
