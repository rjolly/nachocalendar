// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MoonPhase.java

package net.sf.nachocalendar.components;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

// Referenced classes of package net.sf.nachocalendar.components:
//			MoonPhase

class MoonCanvas2 extends Canvas {

	private Dimension d;
	private MoonPhase top;
	private int xmid;
	private int ymid;
	private double scale;
	private double extrasun;

	public MoonCanvas2(MoonPhase parent) {
		top = parent;
		scale = 0.20000000000000001D;
		extrasun = 1.05D;
		setBackground(Color.black);
		setForeground(Color.white);
	}

	protected void paintSun(Graphics g) {
		int size = (int)(((double)d.width * scale * extrasun) / 2D);
		g.setColor(Color.yellow);
		g.fillOval(xmid - size - (int)((double)d.width * scale * (top.phase - 0.5D) * 720D), ymid - size, size * 2, size * 2);
	}

	protected void paintMoon(Graphics g) {
		int size = (int)(((double)d.width * scale) / 2D);
		double p = top.phase;
		p = p >= 0.5D ? p - 0.5D : p + 0.5D;
		int limit = size;
		int sign = p >= 0.5D ? -1 : 1;
		g.setColor(Color.darkGray);
		g.fillOval(xmid - size, ymid - size, size * 2, size * 2);
		g.setColor(Color.white);
		for (int i = -limit; i <= limit; i++)
			g.drawLine(xmid + sign * (int)((double)limit * Math.sqrt(1.0D - (double)(i * i) / (double)(limit * limit))), ymid + i, xmid + sign * (int)((double)limit * Math.cos(6.2831853071795862D * p) * Math.sqrt(1.0D - (double)(i * i) / (double)(limit * limit))), ymid + i);

	}

	public void paintSky(Graphics g) {
		d = getSize();
		xmid = d.width / 2;
		ymid = d.height / 2;
		paintSky(g, d.width, d.height, xmid, ymid);
	}

	public void paintSky(Graphics g, int x, int y, int xm, int ym) {
		xmid = xm;
		ymid = ym;
		d.width = x;
		d.height = y;
		g.setColor(Color.black);
		g.fillRect(0, 0, x, y);
		paintSun(g);
		paintMoon(g);
	}

	public void paint(Graphics g) {
		paintSky(top.gBuf);
		g.drawImage(top.buf, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}
}
