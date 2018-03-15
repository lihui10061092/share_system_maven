package com.lihui.share.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lihui.share.base.BaseController;
import com.lihui.share.entity.Share;
import com.lihui.share.entity.ShareGrade;
import com.lihui.share.entity.User;
import com.lihui.share.service.IShareGradeService;
import com.lihui.share.service.IShareService;
import com.lihui.share.service.IUserGradeService;
import com.lihui.share.service.IUserService;
import com.lihui.share.util.ResultBean;

@Controller
@RequestMapping(value="/shareGrade")
public class ShareGradeController extends BaseController
{
	@Autowired
	private IShareGradeService shareGradeService;
	
	@Autowired
	private IShareService shareService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserGradeService userGradeService;
	
	/**
	 * 用户在首页对其他人的分享进行评分，新增或更新评分
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addOrUpdateShareGrade.do")
	@ResponseBody
	public ResultBean addOrUpdateShareGradeByUserIdAndShareId(HttpServletRequest request, HttpServletResponse response)
	{
		ResultBean rb = ResultBean.getInstance();
		Integer shareId = Integer.parseInt(request.getParameter("s_id"));
//		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String curUserLoginName = (String) request.getSession().getAttribute("username");
		User curUser = userService.queryUserByLoginName(curUserLoginName);
		int userId = curUser.getUser_id();
		double myScore = Double.parseDouble(request.getParameter("myScore"));
		ShareGrade shareGrade = shareGradeService.queryShareGradeByShareIdAndUserId(shareId, userId);
		//如果根据用户id和分享id可以查到评分，则更新；
		if(null != shareGrade)
		{
			shareGradeService.updateShareGradeByShareIdAndUserId(shareId, userId, myScore);
		}
		else
		{//查不到则新增
			shareGradeService.insertShareGrade(shareId, userId, myScore);
		}
		//更新用户评分后，计算和更新该分享的平均分。更新分享人的总得分
		shareService.updateAverageGrade(shareId);
		Share share = shareService.queryShareById(shareId);
		userGradeService.addOrUpdateUserGrade(share.getAuther().getUser_id(), shareId);
		rb.setSuccess(true);
		return rb;
	}
	
}
