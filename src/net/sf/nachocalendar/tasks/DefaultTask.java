// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultTask.java

package net.sf.nachocalendar.tasks;

import java.io.Serializable;
import java.util.Date;

// Referenced classes of package net.sf.nachocalendar.tasks:
//			Task

public class DefaultTask
	implements Task, Serializable {

	private Date date;
	private String name;

	public DefaultTask() {
	}

	public Date getDate() {
		return (Date)date.clone();
	}

	public void setDate(Date date) {
		if (date != null)
			this.date = (Date)date.clone();
		else
			this.date = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
