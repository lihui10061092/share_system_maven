package com.lihui.share.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lihui.share.base.Base;

@Component("myTestQuartz")
public class MyTestQuartz extends Base
{
	@Value("${crmbi.cronExpression.isDoTestJob}")
    private boolean isDoTestJob = false;
    public void execute()
    {
        if(!isDoTestJob)
        {
            return ;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("我是定时任务类，现在的执行时间是" + sdf.format(new Date()));
        
    }
}
