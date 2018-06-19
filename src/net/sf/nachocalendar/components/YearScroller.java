// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   YearScroller.java

package net.sf.nachocalendar.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

// Referenced classes of package net.sf.nachocalendar.components:
//			ArrowButton

public class YearScroller extends JPanel {
	private class FormListener
		implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == bPrevious)
				bPreviousActionPerformed(evt);
			else
			if (evt.getSource() == tYear)
				tYearActionPerformed(evt);
			else
			if (evt.getSource() == bNext)
				bNextActionPerformed(evt);
		}

		private FormListener() {
		}

	}


	private JButton bNext;
	private JButton bPrevious;
	private JFormattedTextField tYear;
	private transient ArrayList changeListenerList;

	public YearScroller() {
		initComponents();
		Calendar cal = Calendar.getInstance();
		tYear.setValue(new Integer(cal.get(1)));
		javax.swing.border.Border border = tYear.getBorder();
		tYear.setBorder(null);
		setBorder(border);
	}

	private void initComponents() {
		bPrevious = new ArrowButton(7);
		try {
			tYear = new JFormattedTextField(new MaskFormatter("####"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		bNext = new ArrowButton(3);
		FormListener formListener = new FormListener();
		setLayout(new GridBagLayout());
		bPrevious.addActionListener(formListener);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weighty = 1.0D;
		add(bPrevious, gridBagConstraints);
		tYear.setColumns(4);
		tYear.setHorizontalAlignment(0);
		tYear.addActionListener(formListener);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		add(tYear, gridBagConstraints);
		bNext.addActionListener(formListener);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weighty = 1.0D;
		add(bNext, gridBagConstraints);
	}

	private void tYearActionPerformed(ActionEvent evt) {
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	private void bNextActionPerformed(ActionEvent evt) {
		setYear(getYear() + 1);
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	private void bPreviousActionPerformed(ActionEvent evt) {
		setYear(getYear() - 1);
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	public void setNextIcon(Icon icon) {
		bNext.setIcon(icon);
	}

	public void setPreviousIcon(Icon icon) {
		bPrevious.setIcon(icon);
	}

	public void setNextText(String text) {
		bNext.setText(text);
	}

	public void setPreviousText(String text) {
		bPrevious.setText(text);
	}

	public int getYear() {
		return ((Number)tYear.getValue()).intValue();
	}

	public void setYear(int year) {
		if (year > 0 && year < 2100)
			tYear.setValue(new Integer(year));
	}

	public synchronized void addChangeListener(ChangeListener listener) {
		if (changeListenerList == null)
			changeListenerList = new ArrayList();
		changeListenerList.add(listener);
	}

	public synchronized void removeChangeListener(ChangeListener listener) {
		if (changeListenerList != null)
			changeListenerList.remove(listener);
	}

	private void fireChangeListenerStateChanged(ChangeEvent event) {
label0:
		{
			synchronized (this) {
				if (changeListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)changeListenerList.clone();
		yearscroller;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		for (int i = 0; i < list.size(); i++)
			((ChangeListener)list.get(i)).stateChanged(event);

		return;
	}

	public void setEnabled(boolean b) {
		bNext.setEnabled(b);
		bPrevious.setEnabled(b);
		tYear.setEnabled(b);
	}

	public boolean isEnabled() {
		return tYear.isEnabled();
	}






}
