package cn.enilu.flash.manage.controller;

import cn.enilu.flash.common.bean.dto.LoginDto;
import cn.enilu.flash.common.bean.entity.system.User;
import cn.enilu.flash.common.bean.state.ManagerStatus;
import cn.enilu.flash.common.bean.vo.front.Rets;
import cn.enilu.flash.common.bean.vo.node.RouterMenu;
import cn.enilu.flash.common.cache.TokenCache;
import cn.enilu.flash.common.log.LogManager;
import cn.enilu.flash.common.log.LogTaskFactory;
import cn.enilu.flash.common.security.ShiroFactroy;
import cn.enilu.flash.common.security.ShiroUser;
import cn.enilu.flash.common.service.system.MenuService;
import cn.enilu.flash.common.utils.*;
import cn.enilu.flash.manage.service.UserService;
import cn.enilu.flash.manage.utils.ApiConstants;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AccountController
 *
 * @author enilu
 * @version 2018/9/12 0012
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private TokenCache tokenCache;

    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     *
     * @param loginDto
     * @return
     */
    @PostMapping(value = "/login")
    public Object login(@RequestBody LoginDto loginDto) {
        try {
            //1,
            String password = loginDto.getPassword();
            String userName = loginDto.getUsername();
            password = CryptUtil.desEncrypt(password);
            User user = userService.findByAccountForLogin(userName);
            if (user == null) {
                return Rets.failure("用户名或密码错误");
            }
            if (user.getStatus() == ManagerStatus.FREEZED.getCode()) {
                return Rets.failure("用户已冻结");
            } else if (user.getStatus() == ManagerStatus.DELETED.getCode()) {
                return Rets.failure("用户已删除");
            }
            String passwdMd5 = MD5.md5(password, user.getSalt());
            //2,
            if (!user.getPassword().equals(passwdMd5)) {
                return Rets.failure("用户名或密码错误");
            }
            if (StringUtil.isEmpty(user.getRoleid())) {
                return Rets.failure("该用户未配置权限");
            }
            String token = userService.loginForToken(user);
            ShiroFactroy.me().shiroUser(token, user);
            Map<String, String> result = new HashMap<>(1);
            result.put("token", token);
            LogManager.me().executeLog(LogTaskFactory.loginLog(user.getId(), HttpUtil.getIp()));
            return Rets.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }

    @GetMapping(value = "/info")
    public Object info() {
        HttpServletRequest request = HttpUtil.getRequest();
        Long idUser = null;
        try {
            idUser = getIdUser(request);
        } catch (Exception e) {
            return Rets.expire();
        }
        if (idUser != null) {
            User user = userService.get(idUser);
            if (user == null) {
                //该用户可能被删除
                return Rets.expire();
            }
            if (StringUtil.isEmpty(user.getRoleid())) {
                return Rets.failure("该用户未配置权限");
            }
            ShiroUser shiroUser = tokenCache.getUser(getToken());
            Map map = Maps.newHashMap("name", user.getName(), "role", "admin", "roles", shiroUser.getRoleCodes());
            List<RouterMenu> list = menuService.getSideBarMenus(shiroUser.getRoleList());
            map.put("menus", list);
            map.put("permissions", shiroUser.getUrls());

            Map profile = (Map) Mapl.toMaplist(user);
            profile.put("dept", shiroUser.getDeptName());
            profile.put("roles", shiroUser.getRoleNames());
            map.put("profile", profile);

            return Rets.success(map);
        }
        return Rets.failure("获取用户信息失败");
    }

    @PostMapping(value = "/updatePwd")
    public Object updatePwd(String oldPassword, String password, String rePassword) {
        try {

            if (StringUtil.isEmpty(password) || StringUtil.isEmpty(rePassword)) {
                return Rets.failure("密码不能为空");
            }
            if (!password.equals(rePassword)) {
                return Rets.failure("新密码前后不一致");
            }
            User user = userService.get(getIdUser(HttpUtil.getRequest()));
            if (ApiConstants.ADMIN_ACCOUNT.equals(user.getAccount())) {
                return Rets.failure("不能修改超级管理员密码");
            }
            if (!MD5.md5(oldPassword, user.getSalt()).equals(user.getPassword())) {
                return Rets.failure("旧密码输入错误");
            }

            user.setPassword(MD5.md5(password, user.getSalt()));
            userService.update(user);
            return Rets.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("更改密码失败");
    }

}
