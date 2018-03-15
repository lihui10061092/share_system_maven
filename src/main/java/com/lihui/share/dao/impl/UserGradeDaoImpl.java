package com.lihui.share.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;

import com.lihui.share.dao.IUserGradeDao;
import com.lihui.share.entity.UserGrade;

//@Repository
public class UserGradeDaoImpl implements IUserGradeDao
{
	@Autowired
	SqlSessionTemplate template;
	
	@Override
	public void insertUserGrade(@Param("u_id")int u_id, @Param("grade")double grade)
	{
		template.insert("insertUserGrade");
	}

	@Override
	public void updateUserGrade(@Param("u_id")int u_id, @Param("grade")double grade)
	{
		template.update("updateUserGrade");
	}

	@Override
	public UserGrade queryUserGradeByUid(@Param("u_id")int u_id)
	{
		return template.selectOne("queryUserGradeByUid");
	}

	@Override
	public List<UserGrade> queryAllUserGrade()
	{
		return template.selectList("queryAllUserGrade");
	}

	@Override
	public void deleteUserGrade(@Param("u_id")int u_id)
	{
		template.delete("deleteUserGradeByUid");
	}

}
