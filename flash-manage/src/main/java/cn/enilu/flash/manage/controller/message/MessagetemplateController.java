package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.MessageTemplate;
import cn.enilu.flash.common.bean.exception.ApplicationException;
import cn.enilu.flash.common.bean.vo.front.Ret;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.enumeration.BizExceptionEnum;
import cn.enilu.flash.common.utils.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/message/template")
public class MessagetemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(required = false) Integer limit,
                       @RequestParam(required = false) Integer page,
                       @RequestParam(name = "idMessageSender", required = false) Long idMessageSender,
                       @RequestParam(name = "title", required = false) String title) {

        Map<String, Object> params = Maps.newHashMap(
                "limit",limit,
                "page",page,
                "idMessageSender", idMessageSender,
                "title", title
        );
        return  restTemplate.getForObject("http://flash-message/message/template/list?limit={limit}&page={page}&idMessageSender={idMessageSender}&title={title}", Ret.class, params);
    }

    @PostMapping
    @BussinessLog(value = "编辑消息模板", key = "name")
    public Object save(@ModelAttribute @Valid MessageTemplate messageTemplate) {
        return restTemplate.postForObject("http://flash-message/message/template", messageTemplate, Ret.class);
    }

    @DeleteMapping
    @BussinessLog(value = "删除消息模板", key = "id")
    public Object remove(Long id) {

        if (id == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        restTemplate.delete("http://flash-message/message/template?id={id}", id);
        return Rets.success();
    }
}
