package cn.enilu.flash.manage.service.message;

import cn.enilu.flash.common.bean.entity.message.MessageSender;
import cn.enilu.flash.common.bean.entity.message.MessageTemplate;
import cn.enilu.flash.common.bean.vo.front.Ret;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * descript
 *
 * @Author enilu
 * @Date 2021/6/29 10:55
 * @Version 1.0
 */

@FeignClient(name="flash-message")
@RequestMapping(path = "/provider/message")
public interface MessageService {
    @GetMapping(value = "/list")
    Ret queryMessagePage(@RequestParam("limit") Integer limit,
                         @RequestParam("page") Integer page,
                         @RequestParam(name="tplCode",required = false) String tplCode,
                         @RequestParam(name="startDate",required = false) String startDate,
                         @RequestParam(name="endDate",required = false) String endDate);
    @DeleteMapping("/clear")
    void clearMessage() ;


    @GetMapping(value = "/template/list")
    Ret queryTemplatePage(@RequestParam Integer limit,
                          @RequestParam Integer page,
                          @RequestParam(name = "idMessageSender", required = false) Long idMessageSender,
                          @RequestParam(name = "title", required = false) String title);
    @GetMapping("/template/queryAll")
    Ret queryAllTmplate() ;
    @PostMapping("/template")
    Ret saveTemplate(@RequestBody MessageTemplate messageTemplate);
    @DeleteMapping("/template/{id}")
    Ret deleteTmplate(@PathVariable("id")  Long id  );


    @GetMapping(value = "/sender/list")
    Ret querySenderPage(@RequestParam("limit") Integer limit,
                        @RequestParam("page") Integer page,
                        @RequestParam(name="name",required = false) String name,
                        @RequestParam(name="className",required = false) String className);
    @GetMapping("/sender/queryAll")
    Ret queryAllSender() ;
    @PostMapping("/sender")
    Ret saveSender(@RequestBody MessageSender messageSender);
    @DeleteMapping("/sender/{id}")
    Ret deleteSender(@PathVariable("id")  Long id  );
}
