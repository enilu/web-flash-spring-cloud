package cn.enilu.flash.common.dao.message;

import cn.enilu.flash.common.bean.entity.message.MessageTemplate;
import cn.enilu.flash.common.dao.BaseRepository;

import java.util.List;

public interface MessagetemplateRepository extends BaseRepository<MessageTemplate, Long> {
    MessageTemplate findByCode(String code);

    List<MessageTemplate> findByIdMessageSender(Long idMessageSender);
}

