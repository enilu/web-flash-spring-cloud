package cn.enilu.flash.message.dao;


import cn.enilu.flash.common.dao.BaseRepository;
import cn.enilu.flash.message.bean.entity.MessageTemplate;

import java.util.List;


public interface MessagetemplateRepository extends BaseRepository<MessageTemplate, Long> {
    MessageTemplate findByCode(String code);

    List<MessageTemplate> findByIdMessageSender(Long idMessageSender);
}

