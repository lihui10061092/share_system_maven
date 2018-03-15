package com.lihui.share.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareConstants
{
	//导出用户模板
	public static final String USER_EXPORT_TEMPLATE = "D:/share_export/User_export_template.xlsx";
	
	public static final String USER_EXPORT_TITLE_XML_PATH = "D:/workspace/share_manager/share_manage_system/src/main/java/com/lihui/share/config/export_user_titles.xml";
	
	public static final List<String> TITLE_LIST = new ArrayList<String>();//Arrrays.asList("","");
	
	public static final Map<String, String> TEST_MAP = new HashMap<String, String>();
	
	//在static代码块中赋值,用户导出标题配置在xml文件中，通过读取xml文件获取
	static
	{
		TEST_MAP.put("", "");
		
		TITLE_LIST.add("用户编号");
		TITLE_LIST.add("工号");
		TITLE_LIST.add("姓名");
		TITLE_LIST.add("登录名");
		TITLE_LIST.add("性别");
		TITLE_LIST.add("邮箱");
		TITLE_LIST.add("手机号");
		TITLE_LIST.add("住址");
		TITLE_LIST.add("公司名称");
		TITLE_LIST.add("所属部门");
		TITLE_LIST.add("项目组");
		TITLE_LIST.add("职位");
		TITLE_LIST.add("级别");
		TITLE_LIST.add("入职日期");
	}
	
	
	
}
