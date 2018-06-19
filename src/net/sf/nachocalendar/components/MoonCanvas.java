// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MoonPhase.java

package net.sf.nachocalendar.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;

// Referenced classes of package net.sf.nachocalendar.components:
//			MoonPhase, MoonCanvas2

class MoonCanvas extends Canvas {

	private Dimension d;
	private MoonPhase top;
	private int xmid;
	private int ymid;
	private double scale;

	public MoonCanvas(MoonPhase parent) {
		top = parent;
		scale = 0.5D;
		setBackground(Color.black);
		setForeground(Color.white);
	}

	protected void paintSun(Graphics g) {
		int size = (int)(((double)d.width * scale * top.sunsize) / 2D);
		g.setColor(Color.yellow);
		g.fillOval(xmid - size - (int)((double)d.width * scale * top.sundist), ymid - size, size * 2, size * 2);
	}

	protected void paintMoon(Graphics g) {
		int size = (int)(((double)d.width * scale * top.moonsize) / 2D);
		g.setColor(Color.darkGray);
		g.fillOval((xmid - size) + (int)((double)d.width * scale * top.moondist * Math.cos(top.phase * 2D * 3.1415926535897931D)), ymid - size - (int)((double)d.width * scale * top.moondist * Math.sin(top.phase * 2D * 3.1415926535897931D)), size * 2, size * 2);
		g.setColor(Color.white);
		g.fillArc((xmid - size) + (int)((double)d.width * scale * top.moondist * Math.cos(top.phase * 2D * 3.1415926535897931D)), ymid - size - (int)((double)d.width * scale * top.moondist * Math.sin(top.phase * 2D * 3.1415926535897931D)), size * 2, size * 2, 90, 180);
	}

	protected void paintEarth(Graphics g) {
		int size = (int)(((double)d.width * scale * top.earthsize) / 2D);
		g.setColor(Color.blue.darker());
		g.fillOval(xmid - size, ymid - size, size * 2, size * 2);
		g.setColor(Color.blue.brighter());
		g.fillArc(xmid - size, ymid - size, size * 2, size * 2, 90, 180);
	}

	protected void paintSky(Graphics g) {
		d = getSize();
		xmid = d.width / 2;
		ymid = d.height / 2;
		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		if (top.both) {
			d.width /= 2;
			top.canvas2.paintSky(g, d.width, d.height, xmid + d.width / 2, ymid);
			g.setColor(Color.black);
			g.fillRect(0, 0, d.width, d.height);
			g.setColor(Color.red);
			g.drawLine(d.width, 0, d.width, d.height);
			xmid = d.width / 2;
		}
		xmid += d.width / 5;
		paintSun(g);
		paintMoon(g);
		paintEarth(g);
	}

	public void paint(Graphics g) {
		paintSky(top.gBuf);
		g.drawImage(top.buf, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public boolean mouseDown(Event evt, int x, int y) {
		return true;
	}

	public boolean mouseUp(Event evt, int x, int y) {
		return true;
	}
}
