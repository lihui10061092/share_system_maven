package com.lihui.share.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lihui.share.base.BaseController;
import com.lihui.share.entity.Announcement;
import com.lihui.share.service.IAnnounceService;
import com.lihui.share.util.ResultBean;

@Controller
@RequestMapping("/announce")
public class AnnounceController extends BaseController
{
	@Autowired
	private IAnnounceService annouceService;
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	//查询标题，显示在首页左侧
	@RequestMapping(value = "/querytitles.do")
	public ResultBean getTitles(HttpServletRequest request, HttpServletResponse response)
	{
		ResultBean rb = ResultBean.getInstance();
		rb.setSuccess(true);
		List<Map> titles = annouceService.queryTitles();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", titles);
		rb.setData(data);
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/queryannounces.do")
	public ResultBean queryAnnounces(HttpServletRequest request, HttpServletResponse response)
	{
		ResultBean rb = ResultBean.getInstance();
		List<Announcement> anns = annouceService.queryAnnounce();
		if(anns.size() == 0)
		{
			rb.setSuccess(false);
		}
		else
		{
			rb.setSuccess(true);
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("result", anns);
		rb.setData(data);
		return rb;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insertannounce.do")
	public ResultBean insertAnnounce(HttpServletRequest request, HttpServletResponse response)
	{
		ResultBean rb = ResultBean.getInstance();
		Map<String, Object> paramMap = this.getAllParamsMap(request);
		
		return rb;
	}
}
