package cn.enilu.flash.manage.controller.front.officialsite;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.cms.Article;
import cn.enilu.flash.common.service.cms.ArticleService;
import cn.enilu.flash.manage.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offcialsite/article")
public class ArticleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Object get(@Param("id") Long id, @Param("type") String type) {
        logger.info("type:{},id:{}", type, id);
        Article article = articleService.get(id);
        return Rets.success(article);
    }
}
