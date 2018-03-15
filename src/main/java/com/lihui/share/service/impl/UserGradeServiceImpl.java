package com.lihui.share.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.share.base.Base;
import com.lihui.share.dao.IShareDao;
import com.lihui.share.dao.IUserGradeDao;
import com.lihui.share.entity.Share;
import com.lihui.share.entity.UserGrade;
import com.lihui.share.service.IUserGradeService;

@Service
public class UserGradeServiceImpl extends Base implements IUserGradeService
{
	//计算用户总得分
	@Autowired
	private IShareDao shareDao;
	
	@Autowired
	private IUserGradeDao userGradeDao;
	
	@Override
	public void addOrUpdateUserGrade(int userId, int shareId)
	{
		UserGrade userGrade = userGradeDao.queryUserGradeByUid(userId);
		//如果当前用户没有得分，则查询该用户的分享列表获取总分插入（处理历史数据）
		if(null == userGrade)
		{
			List<Share> userShareList = shareDao.queryShareListByUserId(userId);
			double userGradeSum = 0.0;
			for(Share share : userShareList)
			{
				userGradeSum += share.getGrade();
			}
			userGradeDao.insertUserGrade(userId, userGradeSum);
		}
		else//如果当前用户有得分，则累加当前分享的评分
		{
			double userCurGrade = userGrade.getGrade();
			Share share = shareDao.queryShareById(shareId);
			double updateGrade = userCurGrade + share.getGrade();
			userGradeDao.updateUserGrade(userId, updateGrade);
		}
	}

}
