// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   FormatSymbols.java

package net.sf.nachocalendar.components;

import java.text.DateFormatSymbols;
import java.util.Locale;
import javax.swing.text.DateFormatter;

public class FormatSymbols extends DateFormatSymbols {

	private static DateFormatSymbols ref;
	private static Locale requiredLocale = Locale.getDefault();

	public FormatSymbols(DateFormatter passedrequiredFormatter, Locale passedrequiredLocale) {
		requiredLocale = passedrequiredLocale;
		ref = null;
		getSingletonObject();
	}

	public static DateFormatSymbols getSingletonObject() {
		if (ref == null)
			synchronized (net.sf.nachocalendar.components.FormatSymbols.class) {
				if (ref == null)
					ref = new DateFormatSymbols(requiredLocale);
			}
		return ref;
	}

}
