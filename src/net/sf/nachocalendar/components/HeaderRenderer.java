// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HeaderRenderer.java

package net.sf.nachocalendar.components;

import java.awt.Component;

// Referenced classes of package net.sf.nachocalendar.components:
//			HeaderPanel

public interface HeaderRenderer {

	public abstract Component getHeaderRenderer(HeaderPanel headerpanel, Object obj, boolean flag, boolean flag1);
}
