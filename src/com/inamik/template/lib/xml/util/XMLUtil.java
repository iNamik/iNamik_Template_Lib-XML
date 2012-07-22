/*
 * $Id: $
 *
 * iNamik Template Lib - XML
 * Copyright (C) 2006 David Farrell (davidpfarrell@yahoo.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.inamik.template.lib.xml.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * XMLUtil
 * Created on Aug 9, 2006
 * @author Dave
 */
public final class XMLUtil
{

	/**
	 * Default Constructor (Private)
	 */
	private XMLUtil() {}

	/**
	 * getDocument w/String
	 */
	public static Document getDocument(final String filename) throws ParserConfigurationException, SAXException, IOException
	{
		return getDocument(new FileInputStream(filename));
	}

	/**
	 * getDocument w/File
	 */
	public static Document getDocument(final File file) throws ParserConfigurationException, SAXException, IOException
	{
		return getDocument(new FileInputStream(file));
	}

	/**
	 * getDocument w/URL
	 */
	public static Document getDocument(final URL url) throws ParserConfigurationException, SAXException, IOException
	{
		return getDocument(url.openStream());
	}

	/**
	 * getDocument w/InputStream
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Document getDocument(final InputStream inStream) throws ParserConfigurationException, SAXException, IOException
	{
		Document document;

		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();

		dfactory.setNamespaceAware(true);

		DocumentBuilder dbuilder = dfactory.newDocumentBuilder();

		document = dbuilder.parse(inStream);

		return document;
	}
}
