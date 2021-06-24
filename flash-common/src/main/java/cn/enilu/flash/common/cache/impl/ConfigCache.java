package cn.enilu.flash.common.cache.impl;

import cn.enilu.flash.common.bean.entity.system.Cfg;
import cn.enilu.flash.common.cache.BaseCache;
import cn.enilu.flash.common.cache.CacheDao;
import cn.enilu.flash.common.cache.IConfigCache;
import cn.enilu.flash.common.dao.system.CfgRepository;
import cn.enilu.flash.common.enumeration.ConfigKeyEnum;
import cn.enilu.flash.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全局参数缓存实现类
 *
 * @author enilu
 * @version 2018/12/20 0020
 */
@Service
public class ConfigCache extends BaseCache implements IConfigCache {
    private static final Logger logger = LoggerFactory.getLogger(ConfigCache.class);
    @Autowired
    private CfgRepository cfgRepository;
    @Autowired
    private CacheDao cacheDao;

    @Override
    public Object get(String key) {
        return cacheDao.hget(CacheDao.CONSTANT, key);
    }

    @Override
    public String get(String key, boolean local) {
        String ret = null;
        if (local) {
            ret = (String) get(key);
        } else {
            ret = cfgRepository.findByCfgName(key).getCfgValue();
            set(key, ret);
        }
        return ret;
    }

    @Override
    public String get(String key, String def) {
        String ret = (String) get(key);
        if (StringUtil.isEmpty(ret)) {
            return ret;
        }
        return ret;
    }

    @Override
    public String get(ConfigKeyEnum configKeyEnum) {
        return get(configKeyEnum.getValue(), null);
    }


    @Override
    public void set(String key, Object val) {
        cacheDao.hset(CacheDao.CONSTANT, key, val);
    }

    @Override
    public void del(String key, String val) {
        cacheDao.hdel(CacheDao.CONSTANT, val);
    }

    @Override
    public void cache() {
        super.cache();
        List<Cfg> list = cfgRepository.findAll();
        if (list != null && !list.isEmpty()) {
            for (Cfg cfg : list) {
                if (StringUtil.isNotEmpty(cfg.getCfgName()) && StringUtil.isNotEmpty(cfg.getCfgValue())) {
                    set(cfg.getCfgName(), cfg.getCfgValue());
                }
            }
        }
    }
}
