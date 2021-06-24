package cn.enilu.flash.manage.controller.system;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.system.Notice;
import cn.enilu.flash.common.service.system.NoticeService;
import cn.enilu.flash.manage.controller.BaseController;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * NoticeController
 *
 * @author enilu
 * @version 2018/12/17 0017
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 获取通知列表
     */
    @GetMapping(value = "/list")
    public Object list(String condition) {
        List<Notice> list = null;
        if (Strings.isNullOrEmpty(condition)) {
            list = noticeService.queryAll();
        } else {
            list = noticeService.findByTitleLike(condition);
        }
        return Rets.success(list);
    }
}
