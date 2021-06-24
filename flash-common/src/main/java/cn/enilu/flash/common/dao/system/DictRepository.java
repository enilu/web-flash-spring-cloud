package cn.enilu.flash.common.dao.system;


import cn.enilu.flash.common.bean.entity.system.Dict;
import cn.enilu.flash.common.dao.BaseRepository;

import java.util.List;

public interface DictRepository extends BaseRepository<Dict, Long> {
    List<Dict> findByPid(Long pid);

    List<Dict> findByNameAndPid(String name, Long pid);

    List<Dict> findByNameLike(String name);
}
