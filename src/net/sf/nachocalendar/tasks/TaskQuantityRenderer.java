// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskQuantityRenderer.java

package net.sf.nachocalendar.tasks;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sf.nachocalendar.components.DayPanel;
import net.sf.nachocalendar.components.DayRenderer;

public class TaskQuantityRenderer extends JLabel
	implements DayRenderer {

	private Calendar cal;
	private Color selectedbg;
	private Color unselectedbg;
	private Color unselectedfg;
	private Color selectedfg;
	private Color notworking;
	private Color taskBg;
	private Color taskColor;
	private int taskq;

	public TaskQuantityRenderer() {
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
		taskColor = Color.RED;
	}

	public Component getDayRenderer(DayPanel daypanel, Date day, Object data, boolean selected, boolean working, boolean enabled) {
		taskq = 0;
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
			taskq = col.size();
			daypanel.setToolTipText(Integer.toString(taskq) + " tasks");
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

	public void paint(Graphics g) {
		super.paint(g);
		if (taskq > 0) {
			int x = getWidth() / 4;
			int y = getHeight() / 4;
			g.setColor(taskColor);
			g.fillOval(x - 1, y - 1, 2, 2);
			if (taskq > 1)
				g.fillOval(x - 1, y * 3 - 1, 2, 2);
			if (taskq > 2)
				g.fillOval(x * 3 - 1, y - 1, 2, 2);
			if (taskq > 3)
				g.fillOval(x * 3 - 1, y * 3 - 1, 2, 2);
		}
	}
}
