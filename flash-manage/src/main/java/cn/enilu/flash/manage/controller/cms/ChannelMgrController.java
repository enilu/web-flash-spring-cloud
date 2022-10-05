package cn.enilu.flash.manage.controller.cms;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.cms.Channel;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.service.cms.ChannelService;
import cn.enilu.flash.manage.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 栏目管理
 */
@RestController
@RequestMapping("/channel")
public class ChannelMgrController extends BaseController {
    @Autowired
    private ChannelService channelService;

    @PostMapping
    @BussinessLog(value = "编辑栏目", key = "name")
    @RequiresPermissions(value = {Permission.CHANNEL_EDIT})
    public Object save(@RequestBody @Valid Channel channel) {
        if (channel.getId() == null) {
            channelService.insert(channel);
        } else {
            channelService.update(channel);
        }
        return Rets.success();
    }

    @DeleteMapping
    @BussinessLog(value = "删除栏目", key = "id")
    @RequiresPermissions(value = {Permission.CHANNEL_DEL})
    public Object remove(Long id) {
        channelService.delete(id);
        return Rets.success();
    }

    @GetMapping(value = "/list")
    @RequiresPermissions(value = {Permission.CHANNEL})
    public Object list() {
        List<Channel> list = channelService.queryAll();
        return Rets.success(list);
    }
}
