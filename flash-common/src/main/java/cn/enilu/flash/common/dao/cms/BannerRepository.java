package cn.enilu.flash.common.dao.cms;


import cn.enilu.flash.common.bean.entity.cms.Banner;
import cn.enilu.flash.common.dao.BaseRepository;

import java.util.List;

public interface BannerRepository extends BaseRepository<Banner, Long> {
    /**
     * 查询指定类别的banner列表
     *
     * @param type
     * @return
     */
    List<Banner> findAllByType(String type);
}
