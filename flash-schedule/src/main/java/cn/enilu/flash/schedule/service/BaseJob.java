package cn.enilu.flash.schedule.service;

import cn.enilu.flash.schedule.bean.QuartzJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        QuartzJob job = (QuartzJob) data.get("job");
        try {
            TaskUtils.executeJob(job);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
