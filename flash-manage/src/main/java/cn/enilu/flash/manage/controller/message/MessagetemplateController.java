package cn.enilu.flash.manage.controller.message;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.message.MessageTemplate;
import cn.enilu.flash.common.bean.exception.ApplicationException;
import cn.enilu.flash.common.enumeration.BizExceptionEnum;
import cn.enilu.flash.manage.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/message/template")
public class MessagetemplateController {

    @Autowired
    private MessageService messageService;
    @GetMapping(value = "/list")
    public Object list(@RequestParam Integer limit,
                       @RequestParam Integer page,
                       @RequestParam(name = "idMessageSender", required = false) Long idMessageSender,
                       @RequestParam(name = "title", required = false) String title) {
        return messageService.queryTemplatePage(limit,page,idMessageSender,title);
    }

    @PostMapping
    @BussinessLog(value = "编辑消息模板", key = "name")
    public Object save(@RequestBody @Valid MessageTemplate messageTemplate) {
        return messageService.saveTemplate(messageTemplate);
//        return restTemplate.postForObject("http://flash-message/message/template", messageTemplate, Ret.class);
    }

    @DeleteMapping
    @BussinessLog(value = "删除消息模板", key = "id")
    public Object remove(Long id) {

        if (id == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        return messageService.deleteTmplate(id);
//        restTemplate.deleteSender("http://flash-message/message/template?id={id}", id);
//        return Rets.success();
    }
}
