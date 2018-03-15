package com.lihui.share.util;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


public class XMLUtil
{
	public static Document getDocumentsByPath(String path)
	{
		Document document = null;
		SAXReader reader = new SAXReader();
		try
		{
			document = reader.read(new File(path));
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return document;
	}
	
//	public static List<String> get
}
