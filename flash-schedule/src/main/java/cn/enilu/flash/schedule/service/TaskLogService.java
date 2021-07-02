package cn.enilu.flash.schedule.service;

import cn.enilu.flash.common.bean.entity.system.TaskLog;
import cn.enilu.flash.common.dao.system.TaskLogRepository;
import cn.enilu.flash.common.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志服务类
 *
 * @author enilu
 * @date 2019-08-13
 */
@Service
public class TaskLogService extends BaseService<TaskLog, Long, TaskLogRepository> {
}
