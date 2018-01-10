package com.lihui.share.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;

import com.lihui.share.dao.IShareGradeDao;
import com.lihui.share.entity.ShareGrade;
//@Repository
public class ShareGradeDaoImpl implements IShareGradeDao
{
	//自动注入
	@Autowired
	private SqlSessionTemplate template;
	
	@Override
	public void insertShareGrade(@Param("s_id")int s_id, @Param("u_id")int u_id, @Param("grade")double grade)
	{
		template.insert("insertShareGrade");
	}

	@Override
	public void updateShareGrade(@Param("s_id")int s_id, @Param("u_id")int u_id, @Param("grade")double grade)
	{
		template.update("updateShareGrade");
	}

	@Override
	public ShareGrade queryShareGradeBySidAndUid(@Param("s_id")int s_id, @Param("u_id")int u_id)
	{
		ShareGrade grade = null;
		grade = template.selectOne("queryShareGradeByUidAndSid");
		return grade;
	}

	@Override
	public List<ShareGrade> queryAllShareGrade()
	{
		List<ShareGrade> grades = template.selectList("queryAllShareGrade");
		return grades;
	}

	@Override
	public void deleteShareGradeBySidAndUid(@Param("s_id")int s_id, @Param("u_id")int u_id)
	{
		template.delete("deleteShareGradeBySidAndUid");
	}

	@Override
	public List<ShareGrade> queryShareGradeByShareId(@Param("s_id")int s_id)
	{
		return template.selectList("queryShareGradeByShareId");
	}

}
