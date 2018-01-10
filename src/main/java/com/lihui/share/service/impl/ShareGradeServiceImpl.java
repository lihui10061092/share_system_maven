package com.lihui.share.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lihui.share.dao.IShareDao;
import com.lihui.share.dao.IShareGradeDao;
import com.lihui.share.entity.Share;
import com.lihui.share.entity.ShareGrade;
import com.lihui.share.service.IShareGradeService;
@Service
public class ShareGradeServiceImpl implements IShareGradeService
{
	@Autowired
	private IShareGradeDao shareGradeDao;
	
	@Autowired
	private IShareDao shareDao;
	
	@Override
	public ShareGrade queryShareGradeByShareIdAndUserId(int shareId, int userId)
	{
		ShareGrade shareGrade = null;
		shareGrade = shareGradeDao.queryShareGradeBySidAndUid(shareId, userId);
		return shareGrade;
	}

	@Override
	public void insertShareGrade(int shareId, int userId, double grade)
	{
		shareGradeDao.insertShareGrade(shareId, userId, grade);
		//新增评分后，分享的评分人数+1
		Share share = shareDao.queryShareById(shareId);
		int currentGradeNum = share.getGrade_num();
		currentGradeNum = currentGradeNum + 1;
		shareDao.updateShare_gradeNum(currentGradeNum, shareId);
	}

	@Override
	public void updateShareGradeByShareIdAndUserId(int shareId, int userId, double grade)
	{
		shareGradeDao.updateShareGrade(shareId, userId, grade);
		//TODO 计算和更新分享平均分
	}


}
