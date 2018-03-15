package com.lihui.share.service;

import java.util.List;
import java.util.Map;

import com.lihui.share.entity.Share;

public interface IShareService
{
//	public List<Share> queryAllShare();
	
	public List<Share> queryShareByPage(int pageIndex, int rowsOfPage);
	
	public Share queryShareById(int shareId);
	
	public void insertShare(Map<String, Object> paramMap);
	
	public void deleteShareById(int shareId);
	
	public void updateShare(Map<String, Object> paramMap);
	
	public int queryAllShareCounts();

//	public List<Share> filterOthersShare(List<Share> allShares, int curUserId);
//	
//	public List<Share> filterMyShare(List<Share> allShares, int curUserId);

	public int queryMyShareCounts(int userId);
	
	public int queryOthersShareCounts(int userId);
	
	public List<Share> queryOthersShareByPage(int pageIndex, int rowsOfPage, int userId);
	
	public List<Share> queryMyShareByPage(int pageIndex, int rowsOfPage, int userId);

	public List<Share> queryAllShareByAdmin(int pageIndex, int row);

	public void updateAverageGrade(int shareId);

	public void updateAdminGrade(int shareId, double adminGrade);
	
}
