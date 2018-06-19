// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MoonPhase.java

package net.sf.nachocalendar.components;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

// Referenced classes of package net.sf.nachocalendar.components:
//			MoonCanvas, MoonCanvas2

public class MoonPhase extends Applet {

	public double phase;
	public double sundist;
	public double moondist;
	public double sunsize;
	public double earthsize;
	public double moonsize;
	public int width;
	public int height;
	public boolean both;
	public Image buf;
	public Graphics gBuf;
	private MoonCanvas canvas;
	public MoonCanvas2 canvas2;
	private TextField fov;
	private Choice viewmenu;
	private Panel center;
	public Canvas current;
	private Button runbutton;

	public MoonPhase() {
	}

	public void init() {
		Dimension d = getSize();
		width = d.width;
		height = d.height;
		setBackground(Color.lightGray);
		setFont(new Font("Helvetica", 0, 12));
		setLayout(new BorderLayout());
		buf = createImage(width, height);
		gBuf = buf.getGraphics();
		phase = 0.0D;
		sundist = 1.0D;
		moondist = 0.29999999999999999D;
		sunsize = 0.20000000000000001D;
		earthsize = 0.14999999999999999D;
		moonsize = 0.070000000000000007D;
		both = false;
		center = new Panel();
		center.setLayout(new CardLayout());
		add("Center", center);
		center.add("Top", canvas = new MoonCanvas(this));
		center.add("Earth", canvas2 = new MoonCanvas2(this));
		canvas2.update(canvas2.getGraphics());
		current = canvas;
		Panel bottom = new Panel();
		bottom.setLayout(new GridLayout(0, 1));
		add("South", bottom);
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(1, 5, 5));
		bottom.add(panel);
		panel.add(new Label("Moon Phase (0.0 to 1.0)", 2));
		fov = new TextField(String.valueOf(phase), 12);
		fov.setEditable(true);
		panel.add(fov);
		runbutton = new Button("Animate");
		panel.add(runbutton);
		panel = new Panel();
		panel.setLayout(new FlowLayout(1, 5, 5));
		bottom.add(panel);
		panel.add(new Label("Point of View", 2));
		viewmenu = new Choice();
		viewmenu.addItem("Top View");
		viewmenu.addItem("Earth View");
		viewmenu.addItem("Both");
		viewmenu.select(0);
		panel.add(viewmenu);
	}

	public void destroy() {
		gBuf.dispose();
	}

	String fixString(double d) {
		String t = String.valueOf((double)Math.round(d * 100000D) / 100000D);
		if (t.endsWith("0001"))
			t = t.substring(0, t.length() - 4);
		for (; t.endsWith("0"); t = t.substring(0, t.length() - 1));
		return t;
	}
}
