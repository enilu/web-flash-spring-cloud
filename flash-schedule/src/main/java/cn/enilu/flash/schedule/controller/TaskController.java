package cn.enilu.flash.schedule.controller;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.system.Task;
import cn.enilu.flash.common.bean.entity.system.TaskLog;
import cn.enilu.flash.common.bean.vo.front.Ret;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.common.web.BaseController;
import cn.enilu.flash.schedule.service.TaskLogService;
import cn.enilu.flash.schedule.service.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created  on 2018/4/9 0009.
 * 系统参数
 *
 * @author enilu
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskLogService taskLogService;


    /**
     * 获取定时任务管理列表
     */
    @GetMapping(value = "/list")
    @RequiresPermissions(value = {Permission.TASK})
    public Object list(String name) {
        if (StringUtil.isNullOrEmpty(name)) {
            return Rets.success(taskService.queryAll());
        } else {
            return Rets.success(taskService.queryAllByNameLike(name));
        }
    }

    /**
     * 新增定时任务管理
     */
    @PostMapping
    @BussinessLog(value = "编辑定时任务", key = "name")
    public Object add(@RequestBody @Valid Task task) {

        Ret validRet = taskService.validate(task);
        if(!validRet.isSuccess()){
            return validRet;
        }
        if (task.getId() == null) {
            taskService.save(task);
        } else {
            Task old = taskService.get(task.getId());
            old.setName(task.getName());
            old.setCron(task.getCron());
            old.setJobClass(task.getJobClass());
            old.setNote(task.getNote());
            old.setData(task.getData());
            taskService.update(old);
        }
        return Rets.success();
    }

    /**
     * 删除定时任务管理
     */
    @DeleteMapping
    @BussinessLog(value = "删除定时任务", key = "taskId")
    public Object delete(@RequestParam Long id) {
        taskService.delete(id);
        return Rets.success();
    }

    @PostMapping(value = "/disable/{taskId}")
    @BussinessLog(value = "禁用定时任务", key = "taskId")
    public Object disable(@PathVariable("taskId") Long taskId) {
        taskService.disable(taskId);
        return Rets.success();
    }

    @PostMapping(value = "/enable/{taskId}")
    @BussinessLog(value = "启用定时任务", key = "taskId")
    public Object enable(@PathVariable("taskId") Long taskId) {
        taskService.enable(taskId);
        return Rets.success();
    }


    @GetMapping(value = "/logList")
    public Object logList(@RequestParam Long taskId) {
        Page<TaskLog> page = new PageFactory<TaskLog>().defaultPage();
        page.addFilter(SearchFilter.build("idTask", SearchFilter.Operator.EQ, taskId));
        page = taskLogService.queryPage(page);
        return Rets.success(page);
    }

}
