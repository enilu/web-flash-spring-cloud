package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.MessageSender;
import cn.enilu.flash.manage.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/message/sender")
public class MessagesenderController {

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = false) Integer limit,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String className) {
        return messageService.querySenderPage(limit,page,name,className);
    }

    @GetMapping(value = "/queryAll")
    public Object queryAll() {
        return messageService.queryAllSender();
    }

    @PostMapping
    @BussinessLog(value = "编辑消息发送者", key = "name")
    public Object save(@ModelAttribute @Valid MessageSender messageSender) {
        return messageService.saveSender(messageSender);

    }

    //
    @DeleteMapping
    @BussinessLog(value = "删除消息发送者", key = "id")
    public Object remove(Long id) {
        return messageService.deleteSender(id);

    }
}