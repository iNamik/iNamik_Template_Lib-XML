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

import com.inamik.template.AbstractTemplateActionTag;
import com.inamik.template.TemplateVariable;
import com.inamik.template.lib.xml.util.XMLUtil;
import com.inamik.template.util.ClassLoaderUtil;

/**
 * Document
 * Created on Aug 9, 2006
 * @author Dave
 */
public final class Document extends AbstractTemplateActionTag
{
	private static final String ATTRIBUTE_ID       = "id";
	private static final String ATTRIBUTE_FILE     = "file";
	private static final String ATTRIBUTE_RESOURCE = "resource";

	/**
	 * Constructor
	 */
	public Document()
	{
		super();
	}

	@Override
	public ExecuteResult execute(Map<String, TemplateVariable> attributes)
	{
		String id       = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_ID      ));
		String file     = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_FILE    ));
		String resource = TemplateVariable.asStringOrNull(attributes.get(ATTRIBUTE_RESOURCE));

		StringBuffer errors = new StringBuffer();

		if (id == null)
		{
			errors.append("Document Action: Missing required attribute '")
			      .append("'").append(ATTRIBUTE_ID).append("'")
			      .append("\n");
		}

		if (file == null && resource == null)
		{
			errors.append("Document Action: Need to provide either ")
			      .append("'").append(ATTRIBUTE_FILE).append("'")
			      .append(" or ")
			      .append("'").append(ATTRIBUTE_RESOURCE).append("'")
			      .append(" attributes\n");
		}

		if (file != null && resource != null)
		{
			errors.append("Document Action: Need to provide either ")
			      .append("'").append(ATTRIBUTE_FILE).append("'")
			      .append(" or ")
			      .append("'").append(ATTRIBUTE_RESOURCE).append("'")
			      .append(" attributes, but not both\n");
		}

		if (errors.length() == 0)
		{
			org.w3c.dom.Document document;

			if (file != null)
			{
				try
				{
					document = XMLUtil.getDocument(getContext().getFileFromTemplateRoot(file));
				}
				catch (Exception e)
				{
					document = null;
					errors.append("Document Action: Exception - " + e.getMessage());
				}
			}
			else
			{
				assert resource != null;

				try
				{
					document = XMLUtil.getDocument(ClassLoaderUtil.getResourceAsStream(resource));
				}
				catch (Exception e)
				{
					document = null;
					errors.append("Document Action: Exception - " + e.getMessage());
				}
			}

			if (document != null)
			{
				assert errors.length() == 0;
				assert id != null;

				getContext().addVariable(id, TemplateVariable.newInstance(document));
			}
		}

		if (errors.length() > 0)
		{
			getContext().getOut().print(errors.toString());
		}

		return ExecuteResult.OK;
	}
}
