// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultDayRenderer.java

package net.sf.nachocalendar.components;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JList;

// Referenced classes of package net.sf.nachocalendar.components:
//			DayRenderer, DayPanel

public class DefaultDayRenderer extends JLabel
	implements DayRenderer {

	private Calendar cal;
	private Color selectedbg;
	private Color unselectedbg;
	private Color selectedfg;
	private Color unselectedfg;
	private Color notworking;

	public DefaultDayRenderer() {
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
		cal.setTime(day);
		setText(Integer.toString(cal.get(5)));
		if (!enabled) {
			setForeground(Color.lightGray);
			return this;
		}
		if (data != null)
			setForeground(Color.RED);
		else
			daypanel.setToolTipText(null);
		return this;
	}
}
