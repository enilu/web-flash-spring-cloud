package cn.enilu.flash.common.service.cms;

import cn.enilu.flash.common.bean.entity.cms.Contacts;
import cn.enilu.flash.common.dao.cms.ContactsRepository;
import cn.enilu.flash.common.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ContactsService extends BaseService<Contacts, Long, ContactsRepository> {
}
