package cn.enilu.flash.common.factory;

import cn.enilu.flash.common.bean.entity.system.Dict;
import cn.enilu.flash.common.bean.state.ManagerStatus;
import cn.enilu.flash.common.bean.vo.DictVo;
import cn.enilu.flash.common.bean.vo.SpringContextHolder;
import cn.enilu.flash.common.cache.CacheKey;
import cn.enilu.flash.common.cache.impl.ConfigCache;
import cn.enilu.flash.common.cache.impl.DictCache;
import cn.enilu.flash.common.dao.system.DictRepository;
import cn.enilu.flash.common.log.LogObjectHolder;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.cache.TimeCacheMap;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 常量的生产工厂
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
@CacheConfig
public class ConstantFactory implements IConstantFactory {
    public static TimeCacheMap<String, String> cache = new TimeCacheMap<String, String>(3600, 2);

    private DictCache dictCache = SpringContextHolder.getBean(DictCache.class);
    private DictRepository dictRepository = SpringContextHolder.getBean(DictRepository.class);

    private ConfigCache configCache = SpringContextHolder.getBean(ConfigCache.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void set(String key, String val) {
        cache.put(key, val);

    }


    @Override
    public List<DictVo> findByDictName(String dictName) {

        List<DictVo> list = new ArrayList<DictVo>();

        List<Dict> dicts = dictCache.getDictsByPname(dictName);
        if (dicts != null) {
            for (int i = 0; i < dicts.size(); i++) {
                Dict dict = dicts.get(i);
                DictVo dictVo = new DictVo(dict.getNum(), dict.getName());
                list.add(dictVo);
            }
        }
        return list;
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Long dictId) {

        String val = get(CacheKey.DICT_NAME + dictId);
        if (StringUtil.isNotEmpty(val)) {
            return val;
        }
        val = dictCache.getDict(dictId);
        set(CacheKey.DICT_NAME + dictId, val);
        return val;

    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, String val) {
        String result = get(CacheKey.DICT_NAME + name + val);
        if (StringUtil.isNotEmpty(result)) {
            return result;
        }
        List<Dict> dicts = dictCache.getDictsByPname(name);
        for (Dict item : dicts) {
            if (item.getNum() != null && item.getNum().equals(val)) {
                result = item.getName();
                set(CacheKey.DICT_NAME + name + val, result);
                return result;
            }
        }
        return "";

    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return getDictsByName("性别", String.valueOf(sex));
    }

    @Override
    public String getCardTypeName(String cardType) {
        return getDictsByName("银行卡类型", cardType);
    }

    @Override
    public String getIdCardTypeName(String cardType) {
        return getDictsByName("证件类型", cardType);
    }

    @Override
    public String getRelationName(String relation) {
        return getDictsByName("联系人关系", relation);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 查询字典
     */
    @Override
    public List<Dict> findInDict(Long id) {
        return dictRepository.findByPid(id);

    }

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    @Override
    public String getCacheObject(String para) {
        return LogObjectHolder.me().get().toString();
    }



    @Override
    public List<Dict> getDicts(String pname) {
        return dictCache.getDictsByPname(pname);
    }

    @Override
    public String getCfg(String cfgName) {
        String val = get(CacheKey.CFG + cfgName);
        if (StringUtil.isNotEmpty(val)) {
            return val;
        }
        val = (String) configCache.get(cfgName);
        set(CacheKey.CFG + cfgName, val);
        return val;
    }

    @Override
    public void cleanLocalCache() {
        cache.neverCleanup();
    }
}
