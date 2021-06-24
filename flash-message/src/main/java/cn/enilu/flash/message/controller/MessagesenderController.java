package cn.enilu.flash.message.controller;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.message.bean.entity.MessageSender;
import cn.enilu.flash.message.service.MessagesenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/message/sender")
public class MessagesenderController {
    @Autowired
    private MessagesenderService messagesenderService;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String className) {
        Page<MessageSender> page = new PageFactory<MessageSender>().defaultPage();
        page.addFilter("name", name);
        page.addFilter("className", className);
        page = messagesenderService.queryPage(page);
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }

    @GetMapping(value = "/queryAll")
    public Object queryAll() {
        return Rets.success(messagesenderService.queryAll());
    }

    @PostMapping
    @BussinessLog(value = "编辑消息发送者", key = "name")
    public Object save(@ModelAttribute @Valid MessageSender messageSender) {
        if(messageSender.getId()!=null){
            MessageSender old = messagesenderService.get(messageSender.getId());
            old.setName(messageSender.getName());
            old.setClassName(messageSender.getClassName());
            messagesenderService.update(old);
        }else {
            MessageSender old = messagesenderService.get(SearchFilter.build("className",messageSender.getClassName()));
            if(old!=null){
                return Rets.failure("改短信发送器已存在，请勿重复添加");
            }
            messagesenderService.insert(messageSender);
        }
        return Rets.success();
    }

    @DeleteMapping
    @BussinessLog(value = "删除消息发送者", key = "id")
    public Object remove(Long id) {

        try {
            messagesenderService.delete(id);
            return Rets.success();
        } catch (Exception e) {
            return Rets.failure(e.getMessage());
        }

    }
}