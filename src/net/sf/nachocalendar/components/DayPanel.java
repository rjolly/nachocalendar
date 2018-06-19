// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DayPanel.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JTable;

// Referenced classes of package net.sf.nachocalendar.components:
//			MoonCalculation, DayRenderer, CalendarUtils

public class DayPanel extends JComponent {

	private Date date;
	private Object data;
	JTable d;
	private boolean antiAliased;
	private DayRenderer renderer;
	private boolean working;
	private boolean componentEnabled;
	private boolean enabled;
	private boolean selected;
	private int index;
	private MoonCalculation moonCalculator;
	private int moonSize;
	private boolean printMoon;

	public DayPanel(DayRenderer renderer, int index) {
		moonSize = 7;
		this.renderer = renderer;
		this.index = index;
		componentEnabled = true;
		setBorder(null);
		setOpaque(false);
		setLayout(new BorderLayout());
		date = new Date();
		setFocusable(true);
		moonCalculator = new MoonCalculation();
	}

	public void setDate(Date d) {
		date = d;
		repaint();
	}

	public Date getDate() {
		return date;
	}

	public void setData(Object d) {
		data = d;
		repaint();
	}

	public Object getData() {
		return data;
	}

	public DayRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(DayRenderer renderer) {
		this.renderer = renderer;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public void paint(Graphics g) {
		Component comp = renderer.getDayRenderer(this, date, data, isSelected(), working, isEnabled() && isComponentEnabled());
		comp.setBounds(getBounds());
		Graphics2D g2 = (Graphics2D)g;
		if (isAntiAliased())
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		comp.paint(g);
		if (isEnabled()) {
			if (CalendarUtils.isToday(date)) {
				g.setColor(Color.red);
				g.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
			}
			if (printMoon && getWidth() > moonSize && getHeight() > moonSize)
				g = moonCalculator.drawMoon(g, date, getWidth() - moonSize - 1, 0, moonSize);
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isComponentEnabled() {
		return componentEnabled;
	}

	public void setComponentEnabled(boolean componentEnabled) {
		this.componentEnabled = componentEnabled;
		super.setEnabled(enabled && componentEnabled);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		super.setEnabled(enabled && componentEnabled);
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		this.antiAliased = antiAliased;
	}

	public boolean isPrintMoon() {
		return printMoon;
	}

	public void setPrintMoon(boolean printMoon) {
		this.printMoon = printMoon;
	}
}
