package com.lihui.share.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.share.base.Base;
import com.lihui.share.constants.ShareConstants;
import com.lihui.share.dao.IShareDao;
import com.lihui.share.dao.IShareGradeDao;
import com.lihui.share.dao.IUserDao;
import com.lihui.share.dao.IUserGradeDao;
import com.lihui.share.entity.User;
import com.lihui.share.service.IUserService;
import com.lihui.share.util.MyExcelUtil;
import com.lihui.share.util.XMLUtil;
/**
 * 
 * @author lihui
 * @Description 用户Service层
 * @date 2017年8月21日
 */
@Service
public class UserServiceImpl extends Base implements IUserService
{
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IShareDao shareDao;
	
	@Autowired
	private IShareGradeDao shareGradeDao;
	
	@Autowired
	private IUserGradeDao userGradeDao;
	
	@Override
	public User queryUserById(int userId)
	{
		return userDao.queryUserById(userId);
	}

	@Override
	public List<User> queryAll()
	{
		List<User> userList = new ArrayList<User>();
		userList = userDao.findAll();
		return userList;
	}

	@Override
	public User queryUserByLoginNameAndPwd(String loginame, String pwd)
	{
		return userDao.queryUserByLoginNameAndPwd(loginame, pwd);
	}

	@Override
	public void updateUser(Map<String, Object> updateParams)
	{
		userDao.updateUser(updateParams);
	}

	@Override
	public boolean deleteUserById(int userId)
	{
		boolean isDelete = true;
//		User user = null;
		userDao.deleteUserById(userId);
		//删除用户后，该用户的分享和分享评分、论坛帖子及评论和回复都要删除
		shareDao.deleteShareByUserId(userId);
		// 删除该用户分享评分、得分、论坛帖子及评论和回复
		shareGradeDao.deleteShareGradeByUserId(userId);
		userGradeDao.deleteUserGrade(userId);
		return isDelete;
	}

	@Override
	public void insertUser(Map<String, Object> addParams)
	{
		userDao.insertUser(addParams);
	}

	@Override
	public User queryUserByLoginName(String loginame)
	{
		return userDao.queryUserByLoginName(loginame);
	}

	@Override
	public List<User> queryUserByPage(int pageIndex, int row)
	{
		int start = (pageIndex - 1) * row;
		int end = pageIndex * row;
		return userDao.queryUserByPage(start, end);
	}

	@Override
	public int queryUserCounts()
	{
		return userDao.queryUserCounts();
	}

	@Override
	public XSSFWorkbook exportAllUser()
	{
		List<User> allUser = userDao.findAll();
		// 导出到excel
		XSSFWorkbook workbook = MyExcelUtil.getWorkbook();
		Sheet sheet = workbook.createSheet("用户Sheet");
		Document document = XMLUtil.getDocumentsByPath(ShareConstants.USER_EXPORT_TITLE_XML_PATH);
		List<String> titleList = getExportUserTitles(document);
		exportUserToWorkbook(allUser, titleList, sheet);
		return workbook;
	}

	/**
	 * 读取XML文档获取导出的列标题
	 * @param document
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getExportUserTitles(Document document)
	{
		List<String> titleList = new ArrayList<String>();
		//获取根节点 root
		Element root = document.getRootElement();
		//获取根节点下的所有子节点
		Iterator<Element> iter = root.elementIterator();
		while (iter.hasNext())
		{
			Element element = iter.next();
			//根据子节点的名称获取值
			titleList.add(element.attributeValue("value"));
		}
		return titleList;
	}

	private void exportUserToWorkbook(List<User> allUser,List<String> titleList, Sheet sheet)
	{
		//拼装导出的数据
		List<Map<String, String>> exportUserData = processUserData(allUser, titleList);
		writeUserDataToSheet(exportUserData, titleList, sheet);
	}

	//将数据写入Sheet中
	private void writeUserDataToSheet(List<Map<String, String>> exportUserData,List<String> titleList, Sheet sheet)
	{
		//写标题行
		writeTitle(titleList, sheet);
		writeUserData(exportUserData, sheet, titleList);
		
	}

	//写数据
	private void writeUserData(List<Map<String, String>> exportUserData, Sheet sheet, List<String> titleList)
	{
		int userLength = exportUserData.size();
		for(int j = 1; j < userLength; j++)
		{
			Map<String, String> userMap = exportUserData.get(j);
			Row row = sheet.createRow(j);
			int titleLength = titleList.size();
			for(int i = 0; i < titleLength; i++)
			{
				if(i == 0)//第一列为编号
				{
					Cell cell = row.createCell(i);
					cell.setCellValue(j);
				}
				else
				{
					Cell cell = row.createCell(i);
					cell.setCellValue(userMap.get(titleList.get(i)));
				}
			}
		}
	}

	private void writeTitle(List<String> titleList, Sheet sheet)
	{
		Row titleRow = sheet.createRow(0);
		//TODO 设置样式
		int titleLength = titleList.size();
		for(int i = 0; i < titleLength; i++)
		{
			if(i == 0)//标题行第一列为编号
			{
				Cell cell = titleRow.createCell(i);
				cell.setCellValue("编号");
			}
			else
			{
				Cell cell = titleRow.createCell(i);
				cell.setCellValue(titleList.get(i));
			}
		}
	}

	//拼装导出的数据
	private List<Map<String, String>> processUserData(List<User> allUser, List<String> titleList)
	{
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (User user : allUser)
		{
			Map<String, String> userMap = new HashMap<String, String>();
			for (String title : titleList)
			{
				switch (title)
				{
				case "用户编号":
					userMap.put(title, String.valueOf(user.getUser_id()));
					break;
				case "工号":
					userMap.put(title, user.getEmp_id());
					break;
				case "姓名":
					userMap.put(title, user.getName());
					break;
				case "登录名":
					userMap.put(title, user.getLoginame());
					break;
				case "性别":
					userMap.put(title, user.getSex());
					break;
				case "邮箱":
					userMap.put(title, user.getEmail());
					break;
				case "手机号":
					userMap.put(title, user.getTel());
					break;
				case "住址":
					userMap.put(title, user.getAddr());
					break;
				case "公司名称":
					userMap.put(title, user.getCompany());
					break;
				case "所属部门":
					userMap.put(title, user.getDept());
					break;
				case "项目组":
					userMap.put(title, user.getProject());
					break;
				case "职位":
					userMap.put(title, user.getPosition());
					break;
				case "级别":
					userMap.put(title, user.getLevel());
					break;
				case "入职日期":
					userMap.put(title, user.getHiredate());
					break;
				default:
					break;

				}

			}
			resultList.add(userMap);
		}
		return resultList;
	}
}
