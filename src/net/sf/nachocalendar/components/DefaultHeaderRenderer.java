// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DefaultHeaderRenderer.java

package net.sf.nachocalendar.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;

// Referenced classes of package net.sf.nachocalendar.components:
//			HeaderRenderer, HeaderPanel

public class DefaultHeaderRenderer extends JLabel
	implements HeaderRenderer {

	public DefaultHeaderRenderer() {
		setOpaque(true);
		Font f = getFont();
		Font n = new Font(f.getName(), 3, f.getSize());
		setFont(n);
		setBackground(Color.GRAY);
		setVerticalAlignment(0);
		setHorizontalAlignment(0);
	}

	public Component getHeaderRenderer(HeaderPanel panel, Object value, boolean isHeader, boolean isWorking) {
		setText(value.toString());
		return this;
	}
}
