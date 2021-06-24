package cn.enilu.flash.message.controller;

import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.exception.ApplicationException;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.enumeration.BizExceptionEnum;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.message.bean.entity.MessageTemplate;
import cn.enilu.flash.message.service.MessagetemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/message/template")
public class MessagetemplateController {
    @Autowired
    private MessagetemplateService messagetemplateService;

    @GetMapping(value = "/list")
    public Object list(@RequestParam(name = "idMessageSender", required = false) Long idMessageSender,
                       @RequestParam(name = "title", required = false) String title) {
        Page<MessageTemplate> page = new PageFactory<MessageTemplate>().defaultPage();
//        page.addFilter("idMessageSender",idMessageSender);
        //也可以通过下面关联查询的方式
        page.addFilter("messageSender.id", idMessageSender);
        page.addFilter("title", SearchFilter.Operator.LIKE, title);

        page = messagetemplateService.queryPage(page);
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }

    @PostMapping
    @BussinessLog(value = "编辑消息模板", key = "name")
    public Object save(@ModelAttribute @Valid MessageTemplate messageTemplate) {
        if (messageTemplate.getId() == null) {

            MessageTemplate old = messagetemplateService.get(SearchFilter.build("code",messageTemplate.getCode()));
            if(old!=null){
                return Rets.failure("模板编码已被使用");
            }
            messagetemplateService.insert(messageTemplate);
        } else {
            messagetemplateService.update(messageTemplate);
        }
        return Rets.success();
    }

    @DeleteMapping
    @BussinessLog(value = "删除消息模板", key = "id")
    public Object remove(Long id) {
        if (id == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        messagetemplateService.delete(id);
        return Rets.success();
    }
}
