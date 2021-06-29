package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.Message;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.utils.Maps;
import cn.enilu.flash.manage.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;
    @Autowired
    private MessageService messageService;
    @GetMapping(value = "/page")
    public Object list(@RequestParam(required = true) Integer limit,
                       @RequestParam(required = true) Integer page,
                       @RequestParam(required = false) String tplCode,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate) {
        return messageService.list(limit,page,tplCode,startDate,endDate);
    }

    @DeleteMapping
    @BussinessLog(value = "清空所有历史消息")
    public Object clear() {
        messageService.clear();
        return Rets.success();
    }

    @PostMapping("/testSender")
    @BussinessLog(value = "发送测试短信")
    public Object testSend(@RequestParam String tplCode,@RequestParam String receiver,@RequestParam String params) {
        Map<String, Object> paramsMap = Maps.newHashMap(

                "tplCode", tplCode,
                "receiver", receiver,
                "params", params
        );
        Object ret = restTemplate.getForObject("http://flash-message/message/list?tplCode={tplCode}&receiver={receiver}&params={params}", Object.class, paramsMap);
        return ret;
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
