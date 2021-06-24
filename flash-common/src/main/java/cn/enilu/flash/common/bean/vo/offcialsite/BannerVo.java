package cn.enilu.flash.common.bean.vo.offcialsite;

import cn.enilu.flash.common.bean.entity.cms.Banner;
import lombok.Data;

import java.util.List;

@Data
public class BannerVo {
    private Integer index = 0;
    private List<Banner> list;

}
