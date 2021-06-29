package cn.enilu.flash.manage.service.message;

import cn.enilu.flash.common.bean.vo.front.Ret;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * descript
 *
 * @Author enilu
 * @Date 2021/6/29 10:55
 * @Version 1.0
 */

@FeignClient(value="flash-message")
public interface MessageService {
    @GetMapping(value = "/list")
    Ret list(@RequestParam(required = false) Integer limit,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) String tplCode,
             @RequestParam(required = false) String startDate,
             @RequestParam(required = false) String endDate);
}
