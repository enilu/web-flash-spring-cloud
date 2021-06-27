package cn.enilu.flash.schedule;

import cn.enilu.flash.common.bean.entity.system.Task;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.schedule.bean.QuartzJob;
import cn.enilu.flash.schedule.service.JobService;
import cn.enilu.flash.schedule.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动定时任务
 *
 * @author enilu
 * @Date 2019-08-13
 */
@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private JobService jobService;
    @Autowired
    private TaskService taskService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("start Job >>>>>>>>>>>>>>>>>>>>>>>");
        List<Task> tasks = taskService.queryAll(SearchFilter.build("disabled", SearchFilter.Operator.EQ, false));
        List<QuartzJob> list = jobService.getTaskList(tasks);
        for (QuartzJob quartzJob : list) {
            jobService.addJob(quartzJob);
        }
    }
}
