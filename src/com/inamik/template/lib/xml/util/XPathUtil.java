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

import java.util.List;

import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;

/**
 * XPathUtil - Borrowed from PScript
 * Created on Aug 8, 2006
 * @author Dave
 */
public final class XPathUtil
{
	/**
	 * Default Constructor (Private)
	 */
	private XPathUtil() {}

	/**
	 * evaluateXPathAsObject
	 */
	public static Object evaluateXPathAsObject(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		return xpath.evaluate(node);
	}

	/**
	 * evaluateXPathAsString
	 */
	public static String evaluateXPathAsString(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		return xpath.stringValueOf(node);
	}

	/**
	 * evaluateXPathAsNumber
	 */
	public static Number evaluateXPathAsNumber(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		return xpath.numberValueOf(node);
	}

	/**
	 * evaluateXPathAsBoolean
	 */
	public static boolean evaluateXPathAsBoolean(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		return xpath.booleanValueOf(node);
	}

	/**
	 * evaluateXPathAsNode
	 */
	public static Node evaluateXPathAsNode(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		// TODO - Not sure if returned object is always a Node
		return (Node)xpath.selectSingleNode(node);
	}

	/**
	 * evaluateXPathAsList
	 */
	public static List evaluateXPathAsList(String xpathString, Object node) throws JaxenException
	{
		XPath xpath;

		xpath = new DOMXPath(xpathString);

		return xpath.selectNodes(node);
	}
}
