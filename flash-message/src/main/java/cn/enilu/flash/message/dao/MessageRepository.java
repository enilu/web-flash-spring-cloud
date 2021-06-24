package cn.enilu.flash.message.dao;



import cn.enilu.flash.common.dao.BaseRepository;
import cn.enilu.flash.message.bean.entity.Message;

import java.util.ArrayList;


public interface MessageRepository extends BaseRepository<Message, Long> {
    void deleteAllByIdIn(ArrayList<String> list);
}

