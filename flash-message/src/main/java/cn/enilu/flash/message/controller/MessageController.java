package cn.enilu.flash.message.controller;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.dto.MessageDto;
import cn.enilu.flash.common.bean.entity.message.Message;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.utils.DateUtil;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.message.service.MessageService;
import org.nutz.json.Json;
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
@RequestMapping("/provider/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = true) Integer limit,
                       @RequestParam(required = true) Integer page,
                       @RequestParam(required = false) String tplCode,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate) {
        Page<Message> pageRet = new PageFactory<Message>().defaultPage();
        pageRet.addFilter("tplCode", tplCode);
        if(StringUtil.isNotEmpty(startDate)) {
            pageRet.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parse(startDate, "yyyyMMddHHmmss"));
        }
        if(StringUtil.isNotEmpty(endDate)) {
            pageRet.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parse(endDate, "yyyyMMddHHmmss"));
        }
        pageRet = messageService.queryPage(pageRet);
        pageRet.setRecords(pageRet.getRecords());
        return Rets.success(pageRet);
    }

    @DeleteMapping("/clear")
    @BussinessLog(value = "清空所有历史消息")
    public Object clear() {
        messageService.clear();
        return Rets.success();
    }

    @PostMapping("/sendTplEmail")
    public Object sendTplEmail(@RequestBody MessageDto messageDto){
        System.out.println(Json.toJson(messageDto));
        messageService.sendTplEmail(messageDto.getTplCode(),messageDto.getTo(),messageDto.getCc(),messageDto.getTitle(),messageDto.getDataParams());
        return Rets.success();
    }
    @PostMapping("/sendSimpleEmail")
    public Object sendSimpleEmail(@RequestBody MessageDto messageDto){
        System.out.println(Json.toJson(messageDto));
        messageService.sendSimpleEmail( messageDto.getTo(),messageDto.getCc(),messageDto.getTitle(),messageDto.getContent());
        return Rets.success();
    }

    @PostMapping("/sendSms")
    public Object sendSms(@RequestBody MessageDto messageDto){
        System.out.println(Json.toJson(messageDto));
        messageService.sendSms(messageDto.getTplCode(),messageDto.getTo(),messageDto.getDataParams());
        return Rets.success();
    }
}
