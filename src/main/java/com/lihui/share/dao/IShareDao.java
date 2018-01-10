package com.lihui.share.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lihui.share.annotation.LHAnnotation;
import com.lihui.share.entity.Share;

@LHAnnotation("iShareDao")
public interface IShareDao
{
	public void insertShare(Map<String, Object> insertParams);
	
	public void deleteShareById(int shareId);
	
	public void updateShare(Map<String, Object> updateParams);
	
	public void updateShare_gradeNum(@Param("grade_num")int grade_num, @Param("s_id")int s_id);
	
	public void updateShare_grade(@Param("grade")double grade, @Param("s_id")int s_id);
	
	public void updateAdminGrade(@Param("ad_grade")int ad_grade, @Param("s_id")int s_id);
	
	public List<Share> queryAllShare();
	
	public Share queryShareById(int shareId);
	
//	public int getUserIdByShareId(int shareId);
	
	public List<Share> queryShareByPage(@Param("start")int start, @Param("end")int end);
	
	public int queryAllShareCounts();

	public int queryMyShareCounts(@Param("userId") int userId);
	
	public List<Share> queryMyShareByPage(@Param("userId") int userId, @Param("start")int start, @Param("end")int end);
	
	public List<Share> queryOthersShareByPage(@Param("userId") int userId, @Param("start")int start, @Param("end")int end);

	public int queryOthersShareCounts(@Param("userId") int userId);

	public void deleteShareByUserId(@Param("userId") int userId);
}
