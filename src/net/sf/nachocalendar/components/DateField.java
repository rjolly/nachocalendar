// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   DateField.java

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import net.sf.nachocalendar.model.DataModel;

// Referenced classes of package net.sf.nachocalendar.components:
//			DatePanel, DefaultDayRenderer, DefaultHeaderRenderer, ArrowButton, 
//			CalendarUtils, DayRenderer, HeaderRenderer

public class DateField extends JPanel
	implements ActionListener, PropertyChangeListener {
	class WindowPanel extends JDialog {

		private void init(boolean showWeekNumbers) {
			setUndecorated(true);
			setFocusable(true);
			JPanel todo = new JPanel(new BorderLayout());
			getContentPane().add(todo);
			todo.add(datepanel);
			todo.setBorder(BorderFactory.createLineBorder(Color.black));
			if (showOkCancel) {
				JPanel abajo = new JPanel();
				todo.add(abajo, "South");
				JButton ok = new JButton("Ok");
				JButton cancel = new JButton("Cancel");
				abajo.add(ok);
				abajo.add(cancel);
				getRootPane().setDefaultButton(ok);
				ok.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						aceptar();
					}

				});
				cancel.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						cancelar();
					}

				});
			} else {
				datepanel.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						aceptar();
					}

				});
			}
			java.awt.event.KeyListener klistener = new KeyAdapter() {

				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\n')
						aceptar();
					if (e.getKeyChar() == '\033')
						cancelar();
				}

			};
			datepanel.addKeyListener(klistener);
			addKeyListener(klistener);
			addWindowListener(new WindowAdapter() {

				public void windowDeactivated(WindowEvent e) {
					cancelar();
				}

			});
			pack();
		}

		public Date getDate() {
			return datepanel.getDate();
		}

		public void setDate(Date d) {
			datepanel.setDate(d);
		}


		public WindowPanel(Frame parent, boolean showWeekNumbers) {
			super(parent, false);
			init(showWeekNumbers);
		}

		public WindowPanel(Dialog parent, boolean showWeekNumbers) {
			super(parent, false);
			init(showWeekNumbers);
		}
	}


	private WindowPanel windowpanel;
	private DatePanel datepanel;
	private JFormattedTextField field;
	private JButton button;
	private Calendar calendar;
	private boolean showWeekNumbers;
	private DateFormatter formatter;
	private boolean antiAliased;
	private boolean printMoon;
	private Locale locale;
	private DateFormat dateFormat;
	private Date baseDate;
	private javax.swing.JFormattedTextField.AbstractFormatterFactory formatterFactory = new javax.swing.JFormattedTextField.AbstractFormatterFactory() {

		public javax.swing.JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
			return new DateFormatter(dateFormat);
		}

	};
	private transient ArrayList changeListenerList;
	private boolean showOkCancel;
	private int firstDayOfWeek;
	private boolean workingDays[];

	public DateField(boolean showWeekNumbers) {
		this.showWeekNumbers = showWeekNumbers;
		init();
	}

	public DateField() {
		this(false);
	}

	public DateField(DateFormatter formatter) {
		this.formatter = formatter;
		init();
	}

	public DateField(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		init();
	}

	public DateField(Locale locale) {
		this.locale = locale;
		init();
	}

	private void init() {
		if (locale == null)
			locale = Locale.getDefault();
		calendar = Calendar.getInstance();
		calendar.set(10, 0);
		calendar.set(12, 0);
		calendar.set(14, 0);
		datepanel = new DatePanel(showWeekNumbers);
		datepanel.setFirstDayOfWeek(firstDayOfWeek);
		datepanel.setWorkingDays(workingDays);
		datepanel.setSelectionMode(0);
		setRenderer(new DefaultDayRenderer());
		setHeaderRenderer(new DefaultHeaderRenderer());
		setLayout(new BorderLayout());
		if (dateFormat != null) {
			field = new JFormattedTextField(dateFormat);
		} else {
			if (formatter == null)
				formatter = new DateFormatter(DateFormat.getDateInstance(3, locale));
			field = new JFormattedTextField(formatter);
		}
		add(field, "Center");
		button = new ArrowButton(5);
		add(button, "East");
		button.addActionListener(this);
		field.setValue(new Date());
		field.addPropertyChangeListener("value", this);
		javax.swing.border.Border border = field.getBorder();
		field.setBorder(null);
		setBorder(border);
		field.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}

		});
	}

	private void aceptar() {
		windowpanel.setVisible(false);
		field.setValue(windowpanel.getDate());
	}

	private void cancelar() {
		windowpanel.setVisible(false);
	}

	private void createWindow() {
		Component c;
		for (c = this; !(c instanceof Dialog) && c != null; c = c.getParent());
		if (c != null) {
			windowpanel = new WindowPanel((Dialog)c, showWeekNumbers);
			return;
		} else {
			Frame f = JOptionPane.getFrameForComponent(this);
			windowpanel = new WindowPanel(f, showWeekNumbers);
			return;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (windowpanel == null)
			createWindow();
		Date da = (Date)field.getValue();
		if (da == null)
			da = baseDate;
		if (da == null)
			da = calendar.getTime();
		windowpanel.setDate(da);
		Point p = getLocationOnScreen();
		p.y += getHeight();
		windowpanel.setLocation(p);
		windowpanel.setVisible(true);
	}

	public void setValue(Object value) {
		try {
			field.setValue(CalendarUtils.convertToDate(value));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Object getValue() {
		return field.getValue();
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
		datefield;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		for (int i = 0; i < list.size(); i++)
			((ChangeListener)list.get(i)).stateChanged(event);

		return;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		fireChangeListenerStateChanged(new ChangeEvent(this));
	}

	public DayRenderer getRenderer() {
		return datepanel.getRenderer();
	}

	public void setRenderer(DayRenderer renderer) {
		datepanel.setRenderer(renderer);
	}

	public DataModel getModel() {
		return datepanel.getModel();
	}

	public void setModel(DataModel model) {
		datepanel.setModel(model);
	}

	public HeaderRenderer getHeaderRenderer() {
		return datepanel.getHeaderRenderer();
	}

	public void setHeaderRenderer(HeaderRenderer headerRenderer) {
		datepanel.setHeaderRenderer(headerRenderer);
	}

	public boolean getShowOkCancel() {
		return showOkCancel;
	}

	public void setShowOkCancel(boolean showOkCancel) {
		if (this.showOkCancel == showOkCancel) {
			return;
		} else {
			boolean old = this.showOkCancel;
			this.showOkCancel = showOkCancel;
			windowpanel = null;
			firePropertyChange("showOkCancel", old, showOkCancel);
			return;
		}
	}

	public boolean getAllowsInvalid() {
		return formatter.getAllowsInvalid();
	}

	public void setAllowsInvalid(boolean b) {
		boolean old = formatter.getAllowsInvalid();
		formatter.setAllowsInvalid(b);
		firePropertyChange("allowsInvalid", old, b);
	}

	public int getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	public void setFirstDayOfWeek(int firstDayOfWeek) {
		int old = this.firstDayOfWeek;
		if (datepanel != null)
			datepanel.setFirstDayOfWeek(firstDayOfWeek);
		this.firstDayOfWeek = firstDayOfWeek;
		firePropertyChange("firstDayOfWeek", old, firstDayOfWeek);
	}

	public boolean[] getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(boolean workingDays[]) {
		boolean old[] = this.workingDays;
		if (datepanel != null)
			datepanel.setWorkingDays(workingDays);
		this.workingDays = workingDays;
		firePropertyChange("workingDays", old, workingDays);
	}

	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
		field.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	public boolean isEnabled() {
		return button.isEnabled();
	}

	public JFormattedTextField getFormattedTextField() {
		return field;
	}

	public boolean isAntiAliased() {
		return antiAliased;
	}

	public void setAntiAliased(boolean antiAliased) {
		boolean old = this.antiAliased;
		this.antiAliased = antiAliased;
		datepanel.setAntiAliased(antiAliased);
		firePropertyChange("antiAliased", old, antiAliased);
	}

	public boolean isPrintMoon() {
		return printMoon;
	}

	public void setPrintMoon(boolean printMoon) {
		datepanel.setPrintMoon(printMoon);
		repaint();
		this.printMoon = printMoon;
	}

	public void setShowToday(boolean show) {
		datepanel.setShowToday(show);
		repaint();
	}

	public boolean getShowToday() {
		return datepanel.getShowToday();
	}

	public void setTodayCaption(String caption) {
		datepanel.setTodayCaption(caption);
	}

	public String getTodayCaption() {
		return datepanel.getTodayCaption();
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		field.setFormatterFactory(formatterFactory);
	}

	public Date getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}






}
