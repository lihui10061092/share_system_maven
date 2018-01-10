package com.lihui.share.entity;
//记录每个用户对分享的评分
public class ShareGrade
{
	private int grade_id;
	//分享id
	private int shareId;
	//评分人id
	private int userId;
	//所评分数
	private double grade;
	
	public int getShareId()
	{
		return shareId;
	}
	public void setShareId(int shareId)
	{
		this.shareId = shareId;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public double getGrade()
	{
		return grade;
	}
	public void setGrade(double grade)
	{
		this.grade = grade;
	}
	
	public int getGrade_id()
	{
		return grade_id;
	}
	public void setGrade_id(int grade_id)
	{
		this.grade_id = grade_id;
	}
	public ShareGrade()
	{
		
	}
	
	public ShareGrade(int grade_id, int shareId, int userId, int grade)
	{
		this.grade_id = grade_id;
		this.shareId = shareId;
		this.userId = userId;
		this.grade = grade;
	}
	
	@Override
	public String toString()
	{
		return "ShareGrade [shareId=" + shareId + ", userId=" + userId + ", grade=" + grade + "]";
	}
	
}
