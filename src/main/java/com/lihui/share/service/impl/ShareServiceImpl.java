package com.lihui.share.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.share.dao.IShareDao;
import com.lihui.share.entity.Share;
import com.lihui.share.entity.ShareGrade;
import com.lihui.share.service.IShareService;

@Service
public class ShareServiceImpl implements IShareService
{

	@Autowired
	private IShareDao shareDao;
	
	@Override
	public List<Share> queryAllShare()
	{
		// TODO Auto-generated method stub
		return null;
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
	public void addShare(Map<String, Object> paramMap)
	{
		shareDao.insertShare(paramMap);
	}

	@Override
	public void deleteShareById(int ShareId)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShare(Map<String, Object> paramMap)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int queryAllShareCounts()
	{
		return shareDao.queryAllShareCounts();
	}

	//过滤分享，从所有分享中去掉自己创建的，用于首页显示。
//	@Override
//	public List<Share> filterOthersShare(List<Share> allShares, int curUserId)
//	{
//		List<Share> filterShareList = new ArrayList<Share>();
//		for(Share share : allShares)
//		{
//			if(!(curUserId == share.getUserId()))
//			{
//				filterShareList.add(share);
//			}
//		}
//		return filterShareList;
//	}

	//过滤分享，从所有分享中去掉自己创建的，用于用户首页显示。
//	@Override
//	public List<Share> filterMyShare(List<Share> allShares, int curUserId)
//	{
//		List<Share> filterShareList = new ArrayList<Share>();
//		for(Share share : allShares)
//		{
//			if(curUserId == share.getUserId())
//			{
//				filterShareList.add(share);
//			}
//		}
//		return filterShareList;
//	}
	
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
		List<Share> shares = shareDao.queryOthersShareByPage(userId, start, end);
		//筛选出自己的评分，用于前端展示 at 20180110
//		String userIdStr = String.valueOf(userId); 没必要转String进行比较
		for(Share share : shares)
		{
			List<ShareGrade> shareGrades = share.getUserGrades();
			//没有用户评分不进行处理
			if(shareGrades.size() == 0 || shareGrades.isEmpty())
			{
				continue;
			}
			else
			{
//				for(ShareGrade grade : shareGrades)
//				{//不能用增强型for循环
//					//把不是当前用户的评分去掉,只返回当前用户的评分到前端，方便前端取数据显示。
//					if((grade.getUserId() - userId) != 0)
//					{
//						shareGrades.remove(index)
//					}
//				}
				for(int i = 0; i < shareGrades.size(); i++)
				{
					ShareGrade grade = shareGrades.get(i);
					if((grade.getUserId() - userId) != 0)
						{
							shareGrades.remove(i);
						}
				}
			}
		}
		return shares;
	}

	@Override
	public List<Share> queryMyShareByPage(int pageIndex, int rowsOfPage, int userId)
	{
		int start = (pageIndex - 1) * rowsOfPage;
		int end = pageIndex * rowsOfPage;
		return shareDao.queryMyShareByPage(userId, start, end);
	}
	
	
}
