// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   MonthScroller.java

package net.sf.nachocalendar.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.nachocalendar.event.YearChangeEvent;
import net.sf.nachocalendar.event.YearChangeListener;

// Referenced classes of package net.sf.nachocalendar.components:
//			ArrowButton, FormatSymbols

public class MonthScroller extends JPanel {
	private class FormListener
		implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() == bPrevious)
				bPreviousActionPerformed(evt);
			else
			if (evt.getSource() == cMonths)
				cMonthsActionPerformed(evt);
			else
			if (evt.getSource() == bNext)
				bNextActionPerformed(evt);
		}

		private FormListener() {
		}

	}


	private JButton bNext;
	private JButton bPrevious;
	private JComboBox cMonths;
	private transient ArrayList yearChangeListenerList;
	private transient ArrayList changeListenerList;

	public MonthScroller() {
		initComponents();
		DateFormatSymbols symbols = FormatSymbols.getSingletonObject();
		String months[] = symbols.getMonths();
		for (int i = 0; i < months.length - 1; i++)
			months[i] = months[i].substring(0, 1).toUpperCase() + months[i].substring(1).toLowerCase();

		cMonths.setModel(new DefaultComboBoxModel(months));
		cMonths.removeItemAt(cMonths.getItemCount() - 1);
	}

	private void initComponents() {
		bPrevious = new ArrowButton(7);
		cMonths = new JComboBox();
		bNext = new ArrowButton(3);
		FormListener formListener = new FormListener();
		setLayout(new GridBagLayout());
		setBorder(new EtchedBorder());
		bPrevious.addActionListener(formListener);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weighty = 1.0D;
		add(bPrevious, gridBagConstraints);
		cMonths.addActionListener(formListener);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weightx = 1.0D;
		gridBagConstraints.weighty = 1.0D;
		add(cMonths, gridBagConstraints);
		bNext.addActionListener(formListener);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = 1;
		gridBagConstraints.weighty = 1.0D;
		add(bNext, gridBagConstraints);
	}

	private void cMonthsActionPerformed(ActionEvent evt) {
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	public void nextMonth() {
		int month = getMonth();
		if (month == 11) {
			setMonth(0);
			fireYearChangeListenerYearIncreased(new YearChangeEvent(this));
		} else {
			setMonth(month + 1);
		}
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	public void previousMonth() {
		int month = getMonth();
		if (month == 0) {
			setMonth(11);
			fireYearChangeListenerYearDecreased(new YearChangeEvent(this));
		} else {
			setMonth(month - 1);
		}
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	private void bNextActionPerformed(ActionEvent evt) {
		nextMonth();
	}

	private void bPreviousActionPerformed(ActionEvent evt) {
		previousMonth();
	}

	public void setMonth(int month) {
		cMonths.setSelectedIndex(month);
	}

	public int getMonth() {
		return cMonths.getSelectedIndex();
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

	public synchronized void addYearChangeListener(YearChangeListener listener) {
		if (yearChangeListenerList == null)
			yearChangeListenerList = new ArrayList();
		yearChangeListenerList.add(listener);
	}

	public synchronized void removeYearChangeListener(YearChangeListener listener) {
		if (yearChangeListenerList != null)
			yearChangeListenerList.remove(listener);
	}

	private void fireYearChangeListenerYearIncreased(YearChangeEvent event) {
label0:
		{
			synchronized (this) {
				if (yearChangeListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)yearChangeListenerList.clone();
		for (int i = 0; i < list.size(); i++)
			((YearChangeListener)list.get(i)).yearIncreased(event);

		return;
	}

	private void fireYearChangeListenerYearDecreased(YearChangeEvent event) {
label0:
		{
			synchronized (this) {
				if (yearChangeListenerList != null)
					break label0;
			}
			return;
		}
		ArrayList list = (ArrayList)yearChangeListenerList.clone();
		for (int i = 0; i < list.size(); i++)
			((YearChangeListener)list.get(i)).yearDecreased(event);

		return;
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
		for (int i = 0; i < list.size(); i++)
			((ChangeListener)list.get(i)).stateChanged(event);

		return;
	}

	public void setEnabled(boolean b) {
		bNext.setEnabled(b);
		bPrevious.setEnabled(b);
		cMonths.setEnabled(b);
	}

	public boolean isEnabled() {
		return cMonths.isEnabled();
	}






}
