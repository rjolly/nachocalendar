// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DateRendererDecorator.java

package net.sf.nachocalendar.table;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class DateRendererDecorator
	implements TableCellRenderer {

	private TableCellRenderer renderer;
	private DateFormat format;

	public DateRendererDecorator() {
		this(((TableCellRenderer) (new DefaultTableCellRenderer())));
	}

	public DateRendererDecorator(TableCellRenderer renderer) {
		this(renderer, DateFormat.getDateInstance(3));
	}

	public DateRendererDecorator(TableCellRenderer renderer, DateFormat format) {
		this.format = format;
		if (renderer == null)
			renderer = new DefaultTableCellRenderer();
		this.renderer = renderer;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isEnabled, int row, int col) {
		if (value != null && (value instanceof Date))
			value = format.format((Date)value);
		Component retorno = renderer.getTableCellRendererComponent(table, value, isSelected, isEnabled, row, col);
		return retorno;
	}
}
