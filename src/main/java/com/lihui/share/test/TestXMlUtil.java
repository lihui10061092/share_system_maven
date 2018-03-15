package com.lihui.share.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import com.lihui.share.util.XMLUtil;

public class TestXMlUtil
{
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDocument()
	{
//		String path = TestXMlUtil.class.getClassLoader().getResource("/com/lihui/share/config/export_user_titles.xml").getPath();
		String path = "D:/workspace/share_manager/share_manage_system/src/main/java/com/lihui/share/config/export_user_titles.xml";
		Document doc = XMLUtil.getDocumentsByPath(path);
		System.out.println(doc);
		List<String> list = new ArrayList<String>();
		Element root = doc.getRootElement();
		Iterator<Element> iter = root.elementIterator();
		while (iter.hasNext())
		{
			Element element = iter.next();
			list.add(element.attributeValue("value"));
		}
		System.out.println(list);
		
	}
}
