// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   ArrowButton.java

package net.sf.nachocalendar.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;

public class ArrowButton extends JButton {

	private int defaultsize;
	private int direction;

	public ArrowButton(int direction) {
		defaultsize = 16;
		this.direction = direction;
	}

	public void paint(Graphics g) {
		super.paint(g);
		int w = getSize().width;
		int h = getSize().height;
		int size = Math.min((h - 4) / 3, (w - 4) / 3);
		size = Math.max(size, 2);
		int x = (w - size) / 2;
		int y = (h - size) / 2;
		int j = 0;
		size = Math.max(size, 2);
		int mid = size / 2 - 1;
		g.translate(x, y);
		if (isEnabled())
			g.setColor(getForeground());
		else
			g.setColor(Color.gray);
		switch (direction) {
		case 2: // '\002'
		case 4: // '\004'
		case 6: // '\006'
		default:
			break;

		case 1: // '\001'
			for (int i = 0; i < size; i++)
				g.drawLine(mid - i, i, mid + i, i);

			break;

		case 5: // '\005'
			j = 0;
			for (int i = size - 1; i >= 0; i--) {
				g.drawLine(mid - i, j, mid + i, j);
				j++;
			}

			break;

		case 7: // '\007'
			for (int i = 0; i < size; i++)
				g.drawLine(i, mid - i, i, mid + i);

			break;

		case 3: // '\003'
			j = 0;
			for (int i = size - 1; i >= 0; i--) {
				g.drawLine(j, mid - i, j, mid + i);
				j++;
			}

			break;
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(defaultsize, defaultsize);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
