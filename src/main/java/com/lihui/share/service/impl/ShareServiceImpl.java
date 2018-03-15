package com.lihui.share.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.share.dao.IShareDao;
import com.lihui.share.dao.IShareGradeDao;
import com.lihui.share.entity.Share;
import com.lihui.share.entity.ShareGrade;
import com.lihui.share.service.IShareService;

@Service
public class ShareServiceImpl implements IShareService
{

	@Autowired
	private IShareDao shareDao;
	
	@Autowired
	private IShareGradeDao shareGradeDao;
	
	@Override
	public List<Share> queryAllShareByAdmin(int pageIndex, int row)
	{
		int start = (pageIndex - 1) * row;
		int end = pageIndex * row;
		return shareDao.queryAllShareByAdmin(start, end);
	}

	@Override
	public List<Share> queryShareByPage(int pageIndex, int rowsOfPage)
	{
		int start = (pageIndex - 1) * rowsOfPage;
		int end = pageIndex * rowsOfPage;
		return shareDao.queryShareByPage(start, end);
	}

	@Override
	public Share queryShareById(int shareId)
	{
		return shareDao.queryShareById(shareId);
	}

	@Override
	public void insertShare(Map<String, Object> paramMap)
	{
		shareDao.insertShare(paramMap);
	}

	@Override
	public void deleteShareById(int shareId)
	{
		shareDao.deleteShareById(shareId);
		//删除分享之后，该分享的评分也要删除。这两个删除作为事务进行管理（已经使用Spring 声明式事务管理）
		shareGradeDao.deleteShareGradeByShareId(shareId);
		
	}

	@Override
	public void updateShare(Map<String, Object> paramMap)
	{
		shareDao.updateShare(paramMap);
	}

	@Override
	public int queryAllShareCounts()
	{
		return shareDao.queryAllShareCounts();
	}

	
	@Override
	public int queryMyShareCounts(int userId)
	{
		return shareDao.queryMyShareCounts(userId);
	}

	@Override
	public int queryOthersShareCounts(int userId)
	{
		return shareDao.queryOthersShareCounts(userId);
	}
	
	@Override
	public List<Share> queryOthersShareByPage(int pageIndex, int rowsOfPage, int userId)
	{
		int start = (pageIndex - 1) * rowsOfPage;
		int end = pageIndex * rowsOfPage;
		List<Share> shareList = shareDao.queryOthersShareByPage(userId, start, end);
		//筛选出自己的评分，用于前端展示 at 20180110
		//在sql语句中处理，否则分页查询每页显示数量可能不正确  at 20181012
//		String userIdStr = String.valueOf(userId); 没必要转String进行比较
//		for(Share share : shares)
//		{
//			List<ShareGrade> shareGrades = share.getUserGrades();
//			//没有用户评分不进行处理
//			if(shareGrades.size() == 0 || shareGrades.isEmpty())
//			{
//				continue;
//			}
//			else
//			{
//				for(int i = 0; i < shareGrades.size(); i++)
//				{
//					ShareGrade grade = shareGrades.get(i);
//					if((grade.getUserId() - userId) != 0)
//						{
//							shareGrades.remove(i);
//						}
//				}
//			}
//		}
		return shareList;
	}

	@Override
	public List<Share> queryMyShareByPage(int pageIndex, int rowsOfPage, int userId)
	{
		int start = (pageIndex - 1) * rowsOfPage;
		int end = pageIndex * rowsOfPage;
		return shareDao.queryMyShareByPage(userId, start, end);
	}

	@Override
	public void updateAverageGrade(int shareId)
	{//计算和更新分享平均分  算法：((用户评分总和S/用户评分数N)+管理员评分AG)/2
		Share share = shareDao.queryShareById(shareId);
		double adminGrade = share.getAdGrade();
		int userGradeNum = share.getGrade_num();//用户评分数，也是shareGradeList 的size
		List<ShareGrade> shareGradeList = shareGradeDao.queryShareGradeByShareId(shareId);
		double userSumGrade = 0.0;
		double averageGrade;
		if(shareGradeList.size() > 0)
		{
			for(ShareGrade shareGrade : shareGradeList)
			{
				userSumGrade += shareGrade.getGrade();
			}
			averageGrade = (userSumGrade/userGradeNum + adminGrade)/2.00;
			
		}
		else
		{//没有用户评分，只有管理员评分
			averageGrade = adminGrade;
		}
		shareDao.updateShare_grade(averageGrade, shareId);
		
	}

	@Override
	public void updateAdminGrade(int shareId, double adminGrade)
	{
		//直接更新管理员评分，不管之前是否有评分 更新之后重新计算平均分。。
		shareDao.updateAdminGrade(adminGrade, shareId);
//		updateAverageGrade(shareId);
	}
	
	
}
