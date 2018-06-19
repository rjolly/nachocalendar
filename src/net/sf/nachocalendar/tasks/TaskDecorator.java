// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   TaskDecorator.java

package net.sf.nachocalendar.tasks;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import java.util.Date;
import net.sf.nachocalendar.components.DayPanel;
import net.sf.nachocalendar.components.DayRenderer;

public class TaskDecorator
	implements DayRenderer {

	private DayRenderer renderer;

	public TaskDecorator(DayRenderer renderer) {
		this.renderer = renderer;
	}

	public Component getDayRenderer(DayPanel daypanel, Date day, Object data, boolean selected, boolean working, boolean enabled) {
		Component retorno = renderer.getDayRenderer(daypanel, day, data, selected, working, enabled);
		if (!enabled)
			return retorno;
		if (data != null && (data instanceof Collection)) {
			if (selected)
				retorno.setBackground(Color.magenta);
			else
				retorno.setBackground(Color.yellow);
			Collection col = (Collection)data;
			daypanel.setToolTipText(Integer.toString(col.size()) + " tasks");
		} else {
			daypanel.setToolTipText(null);
		}
		return retorno;
	}
}
