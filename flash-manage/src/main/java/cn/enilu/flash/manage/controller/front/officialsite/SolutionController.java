package cn.enilu.flash.manage.controller.front.officialsite;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.cms.Article;
import cn.enilu.flash.common.bean.vo.offcialsite.BannerVo;
import cn.enilu.flash.common.bean.vo.offcialsite.Solution;
import cn.enilu.flash.common.enumeration.cms.BannerTypeEnum;
import cn.enilu.flash.common.enumeration.cms.ChannelEnum;
import cn.enilu.flash.common.service.cms.ArticleService;
import cn.enilu.flash.common.service.cms.BannerService;
import cn.enilu.flash.common.utils.Maps;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.manage.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialsite/solution")
public class SolutionController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Object index() {
        Map<String, Object> dataMap = Maps.newHashMap();

        BannerVo banner = bannerService.queryBanner(BannerTypeEnum.SOLUTION.getValue());
        dataMap.put("banner", banner);

        List<Solution> solutions = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.SOLUTION.getId());
        for (Article article : articlePage.getRecords()) {
            solutions.add(new Solution(article.getId(), article.getTitle(), article.getImg()));
        }
        dataMap.put("solutionList", solutions);

        Map map = Maps.newHashMap("data", dataMap);
        return Rets.success(map);

    }
}
