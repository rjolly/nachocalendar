// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DataModel.java

package net.sf.nachocalendar.model;

import java.util.Date;

public interface DataModel {

	public abstract Object getData(Date date);
}
