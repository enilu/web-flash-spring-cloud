package cn.enilu.flash.common.service.cms;

import cn.enilu.flash.common.bean.entity.cms.Banner;
import cn.enilu.flash.common.bean.vo.offcialsite.BannerVo;
import cn.enilu.flash.common.dao.cms.BannerRepository;
import cn.enilu.flash.common.enumeration.cms.BannerTypeEnum;
import cn.enilu.flash.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService extends BaseService<Banner, Long, BannerRepository> {
    @Autowired
    private BannerRepository bannerRepository;

    /**
     * 查询首页banner数据
     *
     * @return
     */
    public BannerVo queryIndexBanner() {
        return queryBanner(BannerTypeEnum.INDEX.getValue());
    }

    public BannerVo queryBanner(String type) {
        BannerVo banner = new BannerVo();
        List<Banner> bannerList = bannerRepository.findAllByType(type);
        banner.setIndex(0);
        banner.setList(bannerList);
        return banner;
    }
}
