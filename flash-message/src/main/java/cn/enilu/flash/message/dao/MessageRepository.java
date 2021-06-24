package cn.enilu.flash.message.dao;


import cn.enilu.flash.common.bean.entity.message.Message;
import cn.enilu.flash.common.dao.BaseRepository;

import java.util.ArrayList;

public interface MessageRepository extends BaseRepository<Message, Long> {
    void deleteAllByIdIn(ArrayList<String> list);
}

