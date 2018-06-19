// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   HolidayDecorator.java

package net.sf.nachocalendar.holidays;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import net.sf.nachocalendar.components.DayPanel;
import net.sf.nachocalendar.components.DayRenderer;

// Referenced classes of package net.sf.nachocalendar.holidays:
//			HoliDay

public class HolidayDecorator
	implements DayRenderer {

	private DayRenderer renderer;

	public HolidayDecorator(DayRenderer renderer) {
		this.renderer = renderer;
	}

	public Component getDayRenderer(DayPanel daypanel, Date day, Object data, boolean selected, boolean working, boolean enabled) {
		Component retorno = renderer.getDayRenderer(daypanel, day, data, selected, working, enabled);
		if (!enabled)
			return retorno;
		if (data != null) {
			retorno.setForeground(Color.RED);
			if (data instanceof HoliDay) {
				HoliDay h = (HoliDay)data;
				daypanel.setToolTipText(h.getName());
			}
		}
		return retorno;
	}
}
