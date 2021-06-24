package cn.enilu.flash.common.service.system;

import cn.enilu.flash.common.bean.entity.system.Notice;
import cn.enilu.flash.common.dao.system.NoticeRepository;
import cn.enilu.flash.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * descript
 *
 * @author ：enilu
 * @date ：Created in 2019/6/30 11:14
 */
@Service
public class NoticeService extends BaseService<Notice, Long, NoticeRepository> {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> findByTitleLike(String title) {
        return noticeRepository.findByTitleLike("%" + title + "%");
    }
}
