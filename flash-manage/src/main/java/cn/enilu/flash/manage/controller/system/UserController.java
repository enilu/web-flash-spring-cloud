package cn.enilu.flash.manage.controller.system;

import cn.enilu.flash.common.aop.BussinessLog;
import cn.enilu.flash.common.bean.dto.UserDto;
import cn.enilu.flash.common.bean.entity.system.User;
import cn.enilu.flash.common.bean.exception.ApplicationException;
import cn.enilu.flash.common.bean.state.ManagerStatus;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.vo.query.SearchFilter;
import cn.enilu.flash.common.enumeration.BizExceptionEnum;
import cn.enilu.flash.common.enumeration.Permission;
import cn.enilu.flash.common.factory.PageFactory;
import cn.enilu.flash.common.factory.UserFactory;
import cn.enilu.flash.common.utils.BeanUtil;
import cn.enilu.flash.common.utils.Constants;
import cn.enilu.flash.common.utils.MD5;
import cn.enilu.flash.common.utils.RandomUtil;
import cn.enilu.flash.common.utils.factory.Page;
import cn.enilu.flash.manage.controller.BaseController;
import cn.enilu.flash.manage.service.UserService;
import cn.enilu.flash.manage.warpper.UserWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * UserController
 *
 * @author enilu
 * @version 2018/9/15 0015
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/list")
    @RequiresPermissions(value = {Permission.USER})
    public Object list(@RequestParam(required = false) String account,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Long deptid,
                       @RequestParam(required = false) String phone,
                       @RequestParam(required = false) Integer status,
                       @RequestParam(required = false) Integer sex
    ) {
        Page page = new PageFactory().defaultPage();
        page.addFilter("name", SearchFilter.Operator.LIKE, name);
        page.addFilter("account", SearchFilter.Operator.LIKE, account);
        page.addFilter("deptid", deptid);
        page.addFilter("phone", phone);
        page.addFilter("status", status);
        page.addFilter("status", SearchFilter.Operator.GT, 0);
        page.addFilter("sex", sex);
        page = userService.queryPage(page);
        List list = (List) new UserWrapper(BeanUtil.objectsToMaps(page.getRecords())).warp();
        page.setRecords(list);
        return Rets.success(page);
    }

    @PostMapping
    @BussinessLog(value = "编辑账号", key = "name")
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object save(@Valid UserDto user, BindingResult result) {
        if (user.getId() == null) {
            // 判断账号是否重复
            User theUser = userService.findByAccount(user.getAccount());
            if (theUser != null) {
                throw new ApplicationException(BizExceptionEnum.USER_ALREADY_REG);
            }
            // 完善账号信息
            user.setSalt(RandomUtil.getRandomString(5));
            user.setPassword(MD5.md5(user.getPassword(), user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            userService.insert(UserFactory.createUser(user, new User()));
        } else {
            User oldUser = userService.get(user.getId());
            userService.update(UserFactory.updateUser(user, oldUser));
        }
        return Rets.success();
    }

    @BussinessLog(value = "删除账号", key = "userId")
    @DeleteMapping
    @RequiresPermissions(value = {Permission.USER_DEL})
    public Object remove(@RequestParam Long userId) {
        if (userId == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if (userId.intValue() <= 2) {
            return Rets.failure("不能删除初始用户");
        }
        User user = userService.get(userId);
        user.setStatus(ManagerStatus.DELETED.getCode());
        userService.update(user);
        return Rets.success();
    }

    @BussinessLog(value = "设置账号角色", key = "userId")
    @PostMapping(value = "/setRole")
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (BeanUtil.isOneEmpty(userId, roleIds)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.intValue() == Constants.ADMIN_ID.intValue()) {
            throw new ApplicationException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        User user = userService.get(userId);
        user.setRoleid(roleIds);
        userService.update(user);
        return Rets.success();
    }

    @BussinessLog(value = "冻结/解冻账号", key = "userId")
    @GetMapping(value = "changeStatus")
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object changeStatus(@RequestParam Long userId) {
        if (userId == null) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if (userId.intValue() <= 2) {
            return Rets.failure("不能冻结初始用户");
        }
        User user = userService.get(userId);
        user.setStatus(user.getStatus().intValue() == ManagerStatus.OK.getCode() ? ManagerStatus.FREEZED.getCode() : ManagerStatus.OK.getCode());
        userService.update(user);
        return Rets.success();
    }
    @BussinessLog(value = "重置密码", key = "userId")
    @PostMapping(value="resetPassword")
    public Object resetPassword(Long userId){
        User user = userService.get(userId);
        user.setPassword(MD5.md5("111111", user.getSalt()));
        userService.update(user);
        return Rets.success();
    }
}
