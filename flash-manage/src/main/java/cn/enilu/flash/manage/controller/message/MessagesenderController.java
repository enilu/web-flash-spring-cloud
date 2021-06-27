package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.MessageSender;
import cn.enilu.flash.common.bean.vo.front.Ret;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/message/sender")
public class MessagesenderController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = false) Integer limit,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String className) {
        Map<String, Object> params = Maps.newHashMap(
                "limit",limit,
                "page",page,
                "name", name,
                "className", className
        );
        return restTemplate.getForObject("http://flash-message/message/sender/list?limit={limit}&page={page}&name={name}&className={className}", Ret.class, params);

    }

    @GetMapping(value = "/queryAll")
    public Object queryAll() {
        return restTemplate.getForObject("http://flash-message/message/sender/queryAll", Ret.class);
    }

    @PostMapping
    @BussinessLog(value = "编辑消息发送者", key = "name")
    public Object save(@ModelAttribute @Valid MessageSender messageSender) {
        return restTemplate.postForObject("http://flash-message/message/sender", messageSender, Ret.class);

    }

    //
    @DeleteMapping
    @BussinessLog(value = "删除消息发送者", key = "id")
    public Object remove(Long id) {
        restTemplate.delete("http://flash-message/message/sender?id={id}", id);
        return Rets.success();

    }
}