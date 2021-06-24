package cn.enilu.flash.common.cache;

import cn.enilu.flash.common.bean.entity.system.Dict;

import java.util.List;

/**
 * 字典缓存
 *
 * @author zt
 * @version 2018/12/23 0023
 */
public interface IDictCache extends Cache {

    List<Dict> getDictsByPname(String dictName);

    String getDict(Long dictId);
}
