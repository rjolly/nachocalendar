// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HeaderPanel.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

// Referenced classes of package net.sf.nachocalendar.components:
//			HeaderRenderer

public class HeaderPanel extends JComponent {

	private boolean antiAliased;
	private HeaderRenderer renderer;
	private Object value;
	private boolean header;
	private boolean working;

	public HeaderPanel(HeaderRenderer renderer) {
		this.renderer = renderer;
		setLayout(new BorderLayout());
		value = "";
	}

	public HeaderRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(HeaderRenderer renderer) {
		this.renderer = renderer;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
		repaint();
	}

	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public void paint(Graphics g) {
		Component comp = renderer.getHeaderRenderer(this, value, header, working);
		comp.setBounds(getBounds());
		Graphics2D g2 = (Graphics2D)g;
		if (isAntiAliased())
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		comp.paint(g);
	}

	public Dimension getPreferredSize() {
		return new Dimension(28, 16);
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		this.antiAliased = antiAliased;
	}
}
