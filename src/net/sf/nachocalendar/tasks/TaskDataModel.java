// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskDataModel.java

package net.sf.nachocalendar.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import net.sf.nachocalendar.model.DefaultDataModel;

// Referenced classes of package net.sf.nachocalendar.tasks:
//			Task

public class TaskDataModel extends DefaultDataModel {

	public TaskDataModel() {
	}

	public void addTask(Task task) {
		Date d = task.getDate();
		if (d == null)
			return;
		Object o = getData(d);
		Collection col = (Collection)o;
		if (col == null) {
			col = new ArrayList();
			addData(d, col);
		}
		col.add(task);
	}

	public Collection getTasks(Date date) {
		Object o = getData(date);
		if (o == null)
			return null;
		if (o instanceof Collection)
			return (Collection)o;
		else
			return null;
	}

	public void removeTask(Task task) {
		Collection col = getTasks(task.getDate());
		if (col != null) {
			col.remove(task);
			if (col.size() == 0)
				removeData(task.getDate());
		}
	}
}
