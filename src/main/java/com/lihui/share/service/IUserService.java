package com.lihui.share.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lihui.share.entity.User;

public interface IUserService
{
	public User queryUserById(int userId);
	
	public List<User> queryAll();
	
	public User queryUserByLoginNameAndPwd(String loginame, String pwd);
	
	public User queryUserByLoginName(String loginame);
	
	public void updateUser(Map<String, Object> updateParams);
	
	public boolean deleteUserById(int userId);
	
	public void insertUser(Map<String, Object> addParams);

	public List<User> queryUserByPage(int pageIndex, int row);

	public int queryUserCounts();

	public XSSFWorkbook exportAllUser();
}
