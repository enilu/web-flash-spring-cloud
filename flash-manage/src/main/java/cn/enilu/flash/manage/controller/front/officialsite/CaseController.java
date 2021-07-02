package cn.enilu.flash.manage.controller.front.officialsite;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.cms.Article;
import cn.enilu.flash.common.bean.vo.offcialsite.BannerVo;
import cn.enilu.flash.common.bean.vo.offcialsite.Product;
import cn.enilu.flash.common.enumeration.cms.BannerTypeEnum;
import cn.enilu.flash.common.enumeration.cms.ChannelEnum;
import cn.enilu.flash.common.service.cms.ArticleService;
import cn.enilu.flash.common.service.cms.BannerService;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.manage.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialsite/case")
public class CaseController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Object index() {
        Map<String, Object> dataMap = new HashMap<>(2);

        BannerVo banner = bannerService.queryBanner(BannerTypeEnum.CASE.getValue());
        dataMap.put("banner", banner);

        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            products.add(new Product(article.getId(), article.getTitle(), article.getImg()));
        }
        dataMap.put("caseList", products);


        Map map = new HashMap(1);

        map.put("data", dataMap);
        return Rets.success(map);

    }
}
