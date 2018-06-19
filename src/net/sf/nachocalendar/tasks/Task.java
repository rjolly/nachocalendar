// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   Task.java

package net.sf.nachocalendar.tasks;

import java.util.Date;

public interface Task {

	public abstract Date getDate();

	public abstract void setDate(Date date);

	public abstract String getName();

	public abstract void setName(String s);
}
