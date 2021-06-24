package cn.enilu.flash.common.service.system;


import cn.enilu.flash.common.bean.entity.system.LoginLog;
import cn.enilu.flash.common.dao.system.LoginLogRepository;
import cn.enilu.flash.common.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created  on 2018/3/26 0026.
 *
 * @author enilu
 */
@Service
public class LoginLogService extends BaseService<LoginLog, Long, LoginLogRepository> {

}
