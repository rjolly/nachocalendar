// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskRenderer.java

package net.sf.nachocalendar.tasks;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sf.nachocalendar.components.DayPanel;
import net.sf.nachocalendar.components.DayRenderer;

/**
 * @deprecated Class TaskRenderer is deprecated
 */

public class TaskRenderer extends JLabel
	implements DayRenderer {

	private Calendar cal;
	private Color selectedbg;
	private Color unselectedbg;
	private Color unselectedfg;
	private Color selectedfg;
	private Color notworking;
	private Color taskBg;

	public TaskRenderer() {
		cal = Calendar.getInstance();
		JList jl = new JList();
		unselectedbg = Color.white;
		unselectedfg = jl.getForeground();
		selectedbg = jl.getSelectionBackground();
		selectedfg = jl.getSelectionForeground();
		setVerticalAlignment(0);
		setHorizontalAlignment(0);
		setOpaque(true);
		notworking = new Color(240, 240, 255);
		taskBg = Color.yellow;
	}

	public Component getDayRenderer(DayPanel daypanel, Date day, Object data, boolean selected, boolean working, boolean enabled) {
		if (selected)
			setBackground(selectedbg);
		else
		if (working)
			setBackground(unselectedbg);
		else
			setBackground(notworking);
		if (working) {
			if (selected)
				setForeground(selectedfg);
			else
				setForeground(unselectedfg);
		} else
		if (selected)
			setForeground(selectedfg);
		else
			setForeground(Color.GRAY);
		if (!enabled) {
			setText("");
			return this;
		}
		cal.setTime(day);
		setText(Integer.toString(cal.get(5)));
		daypanel.setToolTipText(null);
		if (data != null && (data instanceof Collection)) {
			if (selected)
				setBackground(Color.magenta);
			else
				setBackground(taskBg);
			Collection col = (Collection)data;
			daypanel.setToolTipText(Integer.toString(col.size()) + " tasks");
		} else {
			daypanel.setToolTipText(null);
		}
		return this;
	}

	public Color getTaskBg() {
		return taskBg;
	}

	public void setTaskBg(Color taskBg) {
		this.taskBg = taskBg;
	}
}
