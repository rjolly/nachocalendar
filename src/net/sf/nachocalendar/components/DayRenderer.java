// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DayRenderer.java

package net.sf.nachocalendar.components;

import java.awt.Component;
import java.util.Date;

// Referenced classes of package net.sf.nachocalendar.components:
//			DayPanel

public interface DayRenderer {

	public abstract Component getDayRenderer(DayPanel daypanel, Date date, Object obj, boolean flag, boolean flag1, boolean flag2);
}
