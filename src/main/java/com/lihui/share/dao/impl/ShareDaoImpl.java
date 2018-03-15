package com.lihui.share.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.lihui.share.dao.IShareDao;
import com.lihui.share.entity.Share;

/**
 * 
 * @author lihui
 * @Description 分享Dao层CRUD
 * @date 2017年8月23日
 */
//@Repository
public class ShareDaoImpl implements IShareDao
{
	//在Service层中使用Spring AOP进行事务管理
	//自动注入
	@Autowired
	private SqlSessionTemplate tmp;
	//注入SqlSenssionTemplate,由SqlSessionFactory提供
//	@Autowired
//	public void setTemplate(SqlSessionTemplate tmp)
//	{
//		this.tmp = tmp;
//	}
		
	@Override
	public void insertShare(Map<String, Object> insertParams)
	{
		tmp.insert("insertShare", insertParams);
	}

	@Override
	public void deleteShareById(int shareId)
	{
		tmp.delete("deleteShareById", shareId);
	}

	//更新分享
	@Override
	public void updateShare(Map<String, Object> updateParams)
	{
		tmp.update("updateShare", updateParams);
	}
	//更新分享评分人数
	@Override
	public void updateShare_gradeNum(@Param("grade_num")int grade_num, @Param("s_id")int s_id)
	{
		//参数名称要跟xml中取的名字一致
		tmp.update("updateShare_gradeNum");
	}
	//更新分享平均分
	@Override
	public void updateShare_grade(@Param("grade")double grade, @Param("s_id")int s_id)
	{
		tmp.update("updateShare_grade");
	}
	//更新分享
	@Override
	public void updateAdminGrade(@Param("ad_grade")double ad_grade, @Param("s_id")int s_id)
	{
		tmp.update("updateAdminGrade");
	}

	@Override
	public List<Share> queryAllShareByAdmin(@Param("start")int start, @Param("end")int end)
	{
		List<Share> allShare = tmp.selectList("queryAllShareByAdmin");
		return allShare;
	}

	@Override
	public Share queryShareById(int shareId)
	{
		Share share = null;
		share = tmp.selectOne("queryShareById", shareId);
		return share;
	}

	@Override
	public List<Share> queryShareByPage(@Param("start")int start, @Param("end")int end)
	{
		return tmp.selectList("queryShareByPage");
	}

	@Override
	public int queryAllShareCounts()
	{
		return tmp.selectOne("queryAllShareCounts");
	}

	@Override
	public int queryMyShareCounts(int userId)
	{
		return tmp.selectOne("queryMyShareCounts");
	}

	@Override
	public List<Share> queryMyShareByPage(int userId, int start, int end)
	{
		return tmp.selectList("queryMyShareByPage");
	}

	@Override
	public List<Share> queryOthersShareByPage(int userId, int start, int end)
	{
		return tmp.selectList("queryOthersShareByPage");
	}

	@Override
	public int queryOthersShareCounts(int userId)
	{
		return tmp.selectOne("queryOthersShareCounts");
	}

	@Override
	public void deleteShareByUserId(int userId)
	{
		tmp.delete("deleteShareByUserId", userId);
	}

	@Override
	public List<Share> queryShareListByUserId(int userId)
	{
		return null;
	}

}
