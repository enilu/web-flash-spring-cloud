package cn.enilu.flash.message.controller;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.Message;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.utils.DateUtil;
import cn.enilu.flash.common.utils.JsonUtil;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


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

    @PostMapping("/testSender")
    @BussinessLog(value = "发送测试短信")
    public Object testSend(@RequestParam String tplCode,@RequestParam String receiver,@RequestParam String params) {
        LinkedHashMap map = JsonUtil.fromJson(LinkedHashMap.class,params);
        messageService.sendSms(tplCode,receiver,map);
        return Rets.success();
    }
    @GetMapping("/sendTplEmail")
    public Object sendTplEmail(String tplCode,  String to, String cc, String title, Map<String, Object> dataMap){
        Message message = new Message();
        message.setContent("测试发送");
        return Rets.success(message);
    }
    @GetMapping("/sendSimpleEmail")
    public Object sendSimpleEmail(String tplCode,  String to, String cc, String title, Map<String, Object> dataMap){
        Message message = new Message();
        message.setContent("测试发送");
        return Rets.success(message);
    }

    @GetMapping("/sendSms")
    public Object sendSms(String tplCode, String receiver, LinkedHashMap params){
        Message message = new Message();
        message.setContent("测试发送");
        return Rets.success(message);
    }
}
