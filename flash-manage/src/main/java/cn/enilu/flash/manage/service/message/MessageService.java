package cn.enilu.flash.manage.service.message;

import cn.enilu.flash.common.bean.vo.front.Ret;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * descript
 *
 * @Author enilu
 * @Date 2021/6/29 10:55
 * @Version 1.0
 */

@FeignClient(value="flash-message")
@RequestMapping(path = "/message")
public interface MessageService {
    @GetMapping(value = "/list")
    Ret list(@RequestParam("limit") Integer limit,
             @RequestParam("page") Integer page,
             @RequestParam(name="tplCode",required = false) String tplCode,
             @RequestParam(name="startDate",required = false) String startDate,
             @RequestParam(name="endDate",required = false) String endDate);
    @DeleteMapping("/clear")
    void clear() ;
}
