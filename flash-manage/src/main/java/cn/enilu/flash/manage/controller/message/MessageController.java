package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.dto.MessageDto;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.utils.JsonUtil;
import cn.enilu.flash.manage.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * MessageController
 *
 * @Author enilu
 * @Date 2021/6/22 23:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = true) Integer limit,
                       @RequestParam(required = true) Integer page,
                       @RequestParam(required = false) String tplCode,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate) {
        return messageService.queryMessagePage(limit,page,tplCode,startDate,endDate);
    }

    @DeleteMapping
    @BussinessLog(value = "清空所有历史消息")
    public Object clear() {
        messageService.clearMessage();
        return Rets.success();
    }


    @PostMapping("/sendTplEmail")
    public Object sendTplEmail(@RequestBody MessageDto messageDto){
        return messageService.sendTplEmail(messageDto);
    }
    @PostMapping("/sendSimpleEmail")
    public Object sendSimpleEmail(@RequestBody MessageDto messageDto){
        return messageService.sendSimpleEmail(messageDto);
    }

    @PostMapping("/sendSms")
    public Object sendSms(@RequestBody MessageDto messageDto){
        System.out.println(JsonUtil.toJson(messageDto));
       return messageService.sendSms(messageDto);
    }
}
