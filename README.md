iNamik Template Lib - XML
=========================

**Library for [iNamik Template Engine](https://github.com/iNamik/iNamik-Template-Engine) that helps you access XML files within your templates**


TAGS
----

* **{document}**

  Loads an XML file into a template variable.

  Arguments:

    * id [required]
      The name of the template variable to assign the result to.

    * file [optinal]

      Optionally used to load an XMl document from the filesystem.
      If a relative path is provided, it is considered to be relative
      to the Template Root directory.

    * resource [optional]

      Optionally used to load an XMl document from the classpath.

  Filesystem Example:

      {xml:document file="config.xml" id=config}

  Classpath Example:

      {xml:document resource="com/inamik/template/lib/xml/inamik_template_lib-xml.xml" id=templateLib}

* **{xpath}**

  Evaluates and XPath expression against an object.

  Arguments:

    * id [optional]

      Optionally store the result in the specified variable.
      If not specified, the result is printed.

    * from [required]

      The Object to run the XPath expression against.

    * xpath [required]

      The XPath expression.

    * type [optional]

      The desired result type of the XPath expression.

      * boolean
      * node
      * nodes
      * number
      * object
      * string

  Example:

		{xml:document resource="com/inamik/template/lib/xml/inamik_template_lib-xml.xml" id=templateLib}
		{xml:xpath from=$templateLib xpath="template-lib/description" type="string" id=description}
		Template Lib Description:
			{trim}{$description}{/}
		{xml:xpath from=$templateLib xpath="template-lib/action" type="nodes" id=actions}
		Actions:
			{foreach id=action in=$actions}
				{xml:xpath from=$action xpath="@name" type="string"}
					{trim}{xml:xpath from=$action xpath="description" type="string"}{/}
			{/}


REQUIREMENTS
------------

This project has the following dependencies:

* iNamik Template Engine v0.63.0 or later

* Jaxen Universal Java XPath Engine v1.1.1 or later


INSTALLATION
------------

Refer to the [iNamik Template Engine](https://github.com/iNamik/iNamik-Template-Engine) documentation for configuration of template
libraries.


AUTHORS
-------

 * David Farrell
