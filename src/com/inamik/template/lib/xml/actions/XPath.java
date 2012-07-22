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
package com.inamik.template.lib.xml.actions;

import java.util.Map;

import org.jaxen.JaxenException;

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateVariable;
import com.inamik.template.lib.xml.util.XPathUtil;

/**
 * XPath
 * Created on Aug 8, 2006
 * @author Dave
 */
public final class XPath extends AbstractTemplateActionTag
{
	public enum XPathType
	{
		BOOLEAN("boolean"),
		NODE   ("node"   ),
		NODES  ("nodes"  ),
		NUMBER ("number" ),
		OBJECT ("object" ),
		STRING ("string" ),
		;
		private final String name;
		XPathType(final String name) { this.name = name; }
		public String getName() { return this.name; }
		public static XPathType findByName(final String name)
		{
			for (XPathType t : values())
			{
				if (t.name.equalsIgnoreCase(name))
				{
					return t;
				}
			}

			return null;
		}
	}

	private static final String ATTRIBUTE_ID    = "id";
	private static final String ATTRIBUTE_FROM  = "from";
	private static final String ATTRIBUTE_XPATH = "xpath";
	private static final String ATTRIBUTE_TYPE  = "type";

	/**
	 * Constructor
	 */
	public XPath()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes)
	{
		String id    = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID));
		Object from  = TemplateVariable.asObjectOrNull(attributes.get(ATTRIBUTE_FROM));
		String xpath = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_XPATH));
		String type  = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_TYPE));

		XPathType xpathType = null;

		StringBuffer errors = new StringBuffer();

		if (from == null)
		{
			errors.append("XPath Action: Missing required attribute ")
			      .append("'").append(ATTRIBUTE_FROM).append("'")
			      .append("\n");
		}

		if (xpath == null)
		{
			errors.append("XPath Action: Missing required attribute ")
			      .append("'").append(ATTRIBUTE_XPATH).append("'")
			      .append("\n");
		}

		if (type == null)
		{
			if (id == null)
			{
				xpathType = XPathType.STRING;
			}
			else
			{
				xpathType = XPathType.OBJECT;
			}
		}
		else
		{
			xpathType = XPathType.findByName(type);

			if (xpathType == null)
			{
				errors.append("XPath Action: Attribute 'type' has unknown value ")
				      .append("'").append(type).append("'")
				      .append("\n");
			}
		}

		if (errors.length() == 0)
		{
			assert xpathType != null;

			errors.setLength(0);

			TemplateVariable result;

			try
			{
				switch (xpathType)
				{
					case BOOLEAN :
					{
						result = TemplateVariable.valueOf(XPathUtil.evaluateXPathAsBoolean(xpath, from));
						break;
					}

					case NODE :
					{
						result = TemplateVariable.newInstance(XPathUtil.evaluateXPathAsNode(xpath, from));
						break;
					}

					case NODES :
					{
						result = TemplateVariable.newInstance(XPathUtil.evaluateXPathAsList(xpath, from));
						break;
					}

					case NUMBER :
					{
						result = TemplateVariable.newInstance(XPathUtil.evaluateXPathAsNumber(xpath, from));
						break;
					}

					case OBJECT :
					{
						result = TemplateVariable.newInstance(XPathUtil.evaluateXPathAsObject(xpath, from));
						break;
					}

					case STRING :
					{
						result = TemplateVariable.newInstance(XPathUtil.evaluateXPathAsString(xpath, from));
						break;
					}

					default :
					{
						result = null;

						errors.append("XPath Action: Attribute 'type' has unknown value ")
					      .append("'").append(xpathType.getName()).append("'")
					      .append("\n");
					}
				}
			}
			catch (JaxenException je)
			{
				result = null;
				errors.append("XPath Action: Error - ")
				      .append(je.getMessage());
			}

			if (result != null)
			{
				assert errors.length() == 0;

				if (id != null)
				{
					getContext().addVariable(id, result);
				}
				else
				{
					getContext().getOut().print(result.toString());
				}
			}
		}

		if (errors.length() > 0)
		{
			getContext().getOut().print(errors.toString());
		}

		return ExecuteResult.OK;
	}
}
