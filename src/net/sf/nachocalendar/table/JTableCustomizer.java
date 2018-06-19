// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   JTableCustomizer.java

package net.sf.nachocalendar.table;

import java.text.DateFormat;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

// Referenced classes of package net.sf.nachocalendar.table:
//			DateFieldTableEditor, DateRendererDecorator

public final class JTableCustomizer {

	private JTableCustomizer() {
	}

	public static void setDefaultEditor(JTable table) {
		DateFormat format = DateFormat.getDateInstance(3);
		setDefaultEditor(table, format);
	}

	public static void setDefaultEditor(JTable table, DateFormat format) {
		table.setDefaultEditor(java.util.Date.class, new DateFieldTableEditor());
		table.setDefaultRenderer(java.util.Date.class, new DateRendererDecorator(table.getDefaultRenderer(java.util.Date.class), format));
	}

	public static void setEditorForRow(JTable table, int row) {
		DateFormat format = DateFormat.getDateInstance(3);
		setEditorForRow(table, row, format);
	}

	public static void setEditorForRow(JTable table, int row, DateFormat format) {
		TableColumn column = table.getColumnModel().getColumn(row);
		column.setCellEditor(new DateFieldTableEditor());
		column.setCellRenderer(new DateRendererDecorator(column.getCellRenderer(), format));
	}
}
