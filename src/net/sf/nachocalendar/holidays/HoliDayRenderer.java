// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HoliDayRenderer.java

package net.sf.nachocalendar.holidays;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JList;
import net.sf.nachocalendar.components.DayPanel;
import net.sf.nachocalendar.components.DayRenderer;

// Referenced classes of package net.sf.nachocalendar.holidays:
//			HoliDay

/**
 * @deprecated Class HoliDayRenderer is deprecated
 */

public class HoliDayRenderer extends JLabel
	implements DayRenderer {

	private Calendar cal;
	private Color selectedbg;
	private Color unselectedbg;
	private Color unselectedfg;
	private Color selectedfg;
	private Color notworking;

	public HoliDayRenderer() {
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
		daypanel.setToolTipText(null);
		if (!enabled) {
			setForeground(Color.lightGray);
			return this;
		}
		if (!enabled) {
			setForeground(Color.LIGHT_GRAY);
			return this;
		}
		if (data != null) {
			setForeground(Color.RED);
			if (data instanceof HoliDay) {
				HoliDay h = (HoliDay)data;
				daypanel.setToolTipText(h.getName());
			}
		}
		return this;
	}
}
