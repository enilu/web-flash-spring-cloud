package cn.enilu.flash.manage.controller.system;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.system.Task;
import cn.enilu.flash.common.bean.vo.front.Ret;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.utils.Maps;
import cn.enilu.flash.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

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
    private RestTemplate restTemplate;


    /**
     * 获取定时任务管理列表
     */
    @GetMapping(value = "/list")
    @RequiresPermissions(value = {Permission.TASK})
    public Object list(String name) {

        Map<String, Object> params = Maps.newHashMap(
                "name", name
        );
        Object ret = restTemplate.getForObject("http://flash-schedule/task/queryMessagePage?name={name}", Object.class, params);
        return ret;

    }

    /**
     * 新增定时任务管理
     */
    @PostMapping
    @BussinessLog(value = "编辑定时任务", key = "name")
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object add(@ModelAttribute @Valid Task task) {
        Object ret = restTemplate.postForObject("http://flash-schedule/task", task, Ret.class);
        return ret;

    }

    /**
     * 删除定时任务管理
     */
    @DeleteMapping
    @BussinessLog(value = "删除定时任务", key = "taskId")
    @RequiresPermissions(value = {Permission.TASK_DEL})
    public Object delete(@RequestParam Long id) {
        restTemplate.delete("http://flash-schdule/task?id={id}", id);
        return Rets.success();
    }

    @PostMapping(value = "/disable")
    @BussinessLog(value = "禁用定时任务", key = "taskId")
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object disable(@RequestParam Long taskId) {
        return restTemplate.postForObject("http://flash-schedule/task/disable/{taskId}", null, Object.class, taskId);
    }

    @PostMapping(value = "/enable")
    @BussinessLog(value = "启用定时任务", key = "taskId")
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object enable(@RequestParam Long taskId) {

        return restTemplate.postForObject("http://flash-schedule/task/enable/{taskId}", null, Object.class, taskId);
    }


    @GetMapping(value = "/logList")
    @RequiresPermissions(value = {Permission.TASK})
    public Object logList(@RequestParam Integer limit,
                          @RequestParam Integer page,
                          @RequestParam Long taskId) {

        Map<String, Object> params = Maps.newHashMap(
                "limit",limit,
                "page",page,
                "taskId", taskId
        );
        return restTemplate.getForObject("http://flash-schedule/task/logList?limit={limit}&page={page}&taskId={taskId}", Object.class, params);

    }

}
