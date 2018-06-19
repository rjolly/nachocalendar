// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(1000) fieldsfirst nonlb space 
// Source File Name:   XMLCustomizer.java

package net.sf.nachocalendar.customizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// Referenced classes of package net.sf.nachocalendar.customizer:
//			Customizer, PropertiesConverter

public class XMLCustomizer extends DefaultHandler
	implements Customizer {

	private Properties properties;
	private String name;
	private StringBuffer value;

	public XMLCustomizer(InputStream config) throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(config, this);
	}

	public int getInteger(String key) {
		return PropertiesConverter.getInteger(properties.getProperty(key));
	}

	public boolean getBoolean(String key) {
		return PropertiesConverter.getBoolean(properties.getProperty(key));
	}

	public String getString(String key) {
		return properties.getProperty(key);
	}

	public long getLong(String key) {
		return PropertiesConverter.getLong(properties.getProperty(key));
	}

	public float getFloat(String key) {
		return PropertiesConverter.getFloat(properties.getProperty(key));
	}

	public double getDouble(String key) {
		return PropertiesConverter.getDouble(properties.getProperty(key));
	}

	public Set keySet() {
		return properties.keySet();
	}

	public void startDocument() throws SAXException {
		properties = new Properties();
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		value.append(ch, start, length);
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (qName.equals("property"))
			properties.put(name, value.toString());
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (qName.equals("property")) {
			name = atts.getValue("name");
			value = new StringBuffer();
		}
	}
}
