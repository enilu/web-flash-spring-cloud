package cn.enilu.flash.manage.controller.system;

import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.entity.system.Cfg;
import cn.enilu.flash.common.bean.entity.system.FileInfo;
import cn.enilu.flash.common.bean.exception.ApplicationException;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.enumeration.BizExceptionEnum;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.log.LogObjectHolder;
import cn.enilu.flash.common.service.system.CfgService;
import cn.enilu.flash.common.service.system.FileService;
import cn.enilu.flash.common.utils.Maps;
import cn.enilu.flash.common.utils.StringUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.manage.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * CfgController
 *
 * @author enilu
 * @version 2018/11/17 0017
 */
@RestController
@RequestMapping("/cfg")
public class CfgController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CfgService cfgService;
    @Autowired
    private FileService fileService;

    /**
     * 查询参数列表
     */
    @GetMapping(value = "/list")
    @RequiresPermissions(value = {"/cfg"})
    public Object list(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        Page<Cfg> page = new PageFactory<Cfg>().defaultPage();
        if (StringUtil.isNotEmpty(cfgName)) {
            page.addFilter(SearchFilter.build("cfgName", SearchFilter.Operator.LIKE, cfgName));
        }
        if (StringUtil.isNotEmpty(cfgValue)) {
            page.addFilter(SearchFilter.build("cfgValue", SearchFilter.Operator.LIKE, cfgValue));
        }
        page = cfgService.queryPage(page);
        return Rets.success(page);
    }

    /**
     * 导出参数列表
     *
     * @param cfgName
     * @param cfgValue
     * @return
     */
    @GetMapping(value = "/export")
    @RequiresPermissions(value = {Permission.CFG})
    public Object export(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        Page<Cfg> page = new PageFactory<Cfg>().defaultPage();
        if (StringUtil.isNotEmpty(cfgName)) {
            page.addFilter(SearchFilter.build("cfgName", SearchFilter.Operator.LIKE, cfgName));
        }
        if (StringUtil.isNotEmpty(cfgValue)) {
            page.addFilter(SearchFilter.build("cfgValue", SearchFilter.Operator.LIKE, cfgValue));
        }
        page = cfgService.queryPage(page);
        FileInfo fileInfo = fileService.createExcel("templates/config.xlsx", "系统参数.xlsx", Maps.newHashMap("list", page.getRecords()));
        return Rets.success(fileInfo);
    }

    @PostMapping
    @BussinessLog(value = "新增参数", key = "cfgName")
    @RequiresPermissions(value = {"/cfg/add"})
    public Object add(@RequestBody @Valid Cfg cfg) {
        cfgService.saveOrUpdate(cfg);
        return Rets.success();
    }

    @PutMapping
    @BussinessLog(value = "编辑参数", key = "cfgName")
    @RequiresPermissions(value = {"/cfg/update"})
    public Object update(@RequestBody @Valid Cfg cfg) {
        Cfg old = cfgService.get(cfg.getId());
        LogObjectHolder.me().set(old);
        old.setCfgName(cfg.getCfgName());
        old.setCfgValue(cfg.getCfgValue());
        old.setCfgDesc(cfg.getCfgDesc());
        cfgService.saveOrUpdate(old);
        return Rets.success();
    }

    @DeleteMapping
    @BussinessLog(value = "删除参数", key = "id")
    @RequiresPermissions(value = {"/cfg/deleteSender"})
    public Object remove(@RequestParam Long id) {
        logger.info("id:{}", id);
        if (id == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        cfgService.delete(id);
        return Rets.success();
    }
}
