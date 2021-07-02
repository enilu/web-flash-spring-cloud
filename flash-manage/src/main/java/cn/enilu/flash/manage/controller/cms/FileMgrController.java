package cn.enilu.flash.manage.controller.cms;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.entity.system.FileInfo;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.service.system.FileService;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.manage.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileMgr")
public class FileMgrController extends BaseController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/list")
    @RequiresPermissions(value = {Permission.FILE})
    public Object list(@RequestParam(required = false) String originalFileName
    ) {
        Page<FileInfo> page = new PageFactory<FileInfo>().defaultPage();
        if (StringUtil.isNotEmpty(originalFileName)) {
            page.addFilter(SearchFilter.build("originalFileName", SearchFilter.Operator.LIKE, originalFileName));
        }
        page = fileService.queryPage(page);
        return Rets.success(page);
    }
}
