// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MoonCalculation.java

package net.sf.nachocalendar.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.GregorianCalendar;

public class MoonCalculation {

	private int fullMoon;
	private int firstQuaterMoon;
	private int thirdQuaterMoon;
	private static final int day_year[] = {
		-1, -1, 30, 58, 89, 119, 150, 180, 211, 241, 
		272, 303, 333
	};
	private static final String moon_phase_name[] = {
		"New", "Waxing crescent", "First quarter", "Waxing gibbous", "Full", "Waning gibbous", "Third quarter", "Waning crescent"
	};
	private GregorianCalendar calendar;

	public MoonCalculation() {
		fullMoon = 14;
		firstQuaterMoon = 7;
		thirdQuaterMoon = 22;
		calendar = new GregorianCalendar();
	}

	public int moonPhase(int year, int month, int day) {
		if (month < 0 || month > 12)
			month = 0;
		int diy = day + day_year[month];
		if (month > 2 && isLeapYearP(year))
			diy++;
		int cent = year / 100 + 1;
		int golden = year % 19 + 1;
		int epact = ((11 * golden + 20 + (8 * cent + 5) / 25) - 5 - ((3 * cent) / 4 - 12)) % 30;
		if (epact <= 0)
			epact += 30;
		if (epact == 25 && golden > 11 || epact == 24)
			epact++;
		int phase = (((diy + epact) * 6 + 11) % 177) / 6;
		return phase;
	}

	int daysInMonth(int month, int year) {
		int result = 31;
		switch (month) {
		case 4: // '\004'
		case 6: // '\006'
		case 9: // '\t'
		case 11: // '\013'
			result = 30;
			break;

		case 2: // '\002'
			result = isLeapYearP(year) ? 29 : 28;
			break;
		}
		return result;
	}

	public boolean isLeapYearP(int year) {
		return year % 4 == 0 && (year % 400 == 0 || year % 100 != 0);
	}

	String phaseName(int phase) {
		return moon_phase_name[phase];
	}

	public Graphics drawMoon(Graphics g, Date d, int posX, int posY, int size) {
		calendar.setTime(d);
		int newPhase = moonPhase(calendar.get(1), calendar.get(2) + 1, calendar.get(5));
		if (newPhase == 0) {
			g.setColor(Color.BLACK);
			g.fillOval(posX, posY, size, size);
		} else
		if (newPhase == fullMoon) {
			g.setColor(Color.BLACK);
			g.drawOval(posX, posY, size, size);
			g.setColor(Color.yellow);
			g.fillOval(posX + 1, posY + 1, size - 1, size - 2);
		} else
		if (newPhase == firstQuaterMoon) {
			g.setColor(Color.BLACK);
			g.drawOval(posX, posY, size, size);
			g.fillArc(posX + 1, posY + 1, size - 1, size - 1, 90, 180);
			g.setColor(Color.yellow);
			g.fillArc(posX + 1, posY + 1, size - 1, size - 1, 270, 180);
		} else
		if (newPhase == thirdQuaterMoon) {
			g.setColor(Color.BLACK);
			g.drawOval(posX, posY, size, size);
			g.fillArc(posX + 1, posY + 1, size - 1, size - 1, 270, 180);
			g.setColor(Color.yellow);
			g.fillArc(posX + 1, posY + 1, size - 1, size - 1, 90, 180);
		}
		return g;
	}

}
