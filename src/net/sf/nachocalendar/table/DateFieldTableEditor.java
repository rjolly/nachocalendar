// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DateFieldTableEditor.java

package net.sf.nachocalendar.table;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;

public class DateFieldTableEditor extends AbstractCellEditor
	implements TableCellEditor {

	private DateField datefield;

	public DateFieldTableEditor() {
		datefield = CalendarFactory.createDateField();
	}

	public DateFieldTableEditor(DateField datefield) {
		this.datefield = datefield;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		datefield.setValue(value);
		return datefield;
	}

	public Object getCellEditorValue() {
		return datefield.getValue();
	}

	public boolean isCellEditable(EventObject evt) {
		if (evt instanceof MouseEvent) {
			MouseEvent me = (MouseEvent)evt;
			return me.getClickCount() > 1;
		} else {
			return super.isCellEditable(evt);
		}
	}
}
