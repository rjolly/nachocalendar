// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   CustomizerFactory.java

package net.sf.nachocalendar.customizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;
import org.xml.sax.SAXException;

// Referenced classes of package net.sf.nachocalendar.customizer:
//			DirectSetter, PropertiesCustomizer, XMLCustomizer, PropertiesSetter

public class CustomizerFactory {

	PropertiesSetter setter;

	public CustomizerFactory(File config) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
		if (config.getName().toLowerCase().endsWith(".properties"))
			setter = new DirectSetter(new PropertiesCustomizer(new FileInputStream(config)));
		if (config.getName().toLowerCase().endsWith(".xml"))
			setter = new DirectSetter(new XMLCustomizer(new FileInputStream(config)));
		if (setter == null)
			throw new IllegalArgumentException("Configuration file not valid");
		else
			return;
	}

	public DateField createDateField() {
		DateField retorno = new DateField();
		setter.customize(retorno);
		return retorno;
	}

	public DateField createDateField(boolean showWeekNumbers) {
		DateField retorno = new DateField(showWeekNumbers);
		setter.customize(retorno);
		return retorno;
	}

	public DatePanel createDatePanel() {
		DatePanel retorno = new DatePanel();
		setter.customize(retorno);
		return retorno;
	}

	public DatePanel createDatePanel(boolean showWeekNumbers) {
		DatePanel retorno = new DatePanel(showWeekNumbers);
		setter.customize(retorno);
		return retorno;
	}

	public CalendarPanel createCalendarPanel() {
		CalendarPanel retorno = new CalendarPanel();
		setter.customize(retorno);
		return retorno;
	}

	public CalendarPanel createCalendarPanel(boolean showWeekNumbers) {
		CalendarPanel retorno = new CalendarPanel(showWeekNumbers);
		setter.customize(retorno);
		return retorno;
	}
}
