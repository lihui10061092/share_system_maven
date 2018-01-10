package com.lihui.share.service;

import com.lihui.share.entity.ShareGrade;

public interface IShareGradeService
{
	public ShareGrade queryShareGradeByShareIdAndUserId(int shareId, int userId);
	
	public void insertShareGrade(int shareId, int userId, double grade);
	
	public void updateShareGradeByShareIdAndUserId(int shareId, int userId, double grade);
}
