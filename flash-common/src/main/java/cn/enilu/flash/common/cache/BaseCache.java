package cn.enilu.flash.common.cache;


import cn.enilu.flash.common.bean.vo.SpringContextHolder;
import cn.enilu.flash.common.factory.ConstantFactory;

/**
 * @author ：enilu
 * @date ：Created in 2020/4/26 19:07
 */
public abstract class BaseCache implements Cache {
    @Override
    public void cache() {
        SpringContextHolder.getBean(ConstantFactory.class).cleanLocalCache();
    }
}
