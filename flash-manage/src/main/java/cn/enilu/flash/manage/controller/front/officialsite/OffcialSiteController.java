package cn.enilu.flash.manage.controller.front.officialsite;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.cms.Article;
import cn.enilu.flash.common.bean.vo.offcialsite.BannerVo;
import cn.enilu.flash.common.bean.vo.offcialsite.News;
import cn.enilu.flash.common.bean.vo.offcialsite.Product;
import cn.enilu.flash.common.bean.vo.offcialsite.Solution;
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
@RequestMapping("/offcialsite")
public class OffcialSiteController extends BaseController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Object index() {
        Map<String, Object> dataMap = Maps.newHashMap();

        BannerVo banner = bannerService.queryIndexBanner();
        dataMap.put("banner", banner);
        List<News> newsList = new ArrayList<>();
        List<Article> articles = articleService.queryIndexNews();
        for (Article article : articles) {
            News news = new News();
            news.setDesc(article.getTitle());
            news.setUrl("/article?id=" + article.getId());
            news.setId(article.getId());
            news.setCreateTime(article.getCreateTime());
            news.setSrc("static/images/icon/user.png");
            newsList.add(news);
        }
        dataMap.put("newsList", newsList);

        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 4, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            Product product = new Product();
            product.setId(article.getId());
            product.setName(article.getTitle());
            product.setImg(article.getImg());
            products.add(product);
        }
        dataMap.put("productList", products);

        List<Solution> solutions = new ArrayList<>();
        articlePage = articleService.query(1, 4, ChannelEnum.SOLUTION.getId());
        for (Article article : articlePage.getRecords()) {
            Solution solution = new Solution();
            solution.setId(article.getId());
            solution.setName(article.getTitle());
            solution.setImg(article.getImg());
            solutions.add(solution);
        }
        dataMap.put("solutionList", solutions);
        Map map = Maps.newHashMap("data", dataMap);
        return Rets.success(map);

    }
}
