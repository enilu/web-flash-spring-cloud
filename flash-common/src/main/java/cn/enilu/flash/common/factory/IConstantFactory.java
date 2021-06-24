package cn.enilu.flash.common.factory;


import cn.enilu.flash.common.bean.entity.system.Dict;
import cn.enilu.flash.common.bean.vo.DictVo;

import java.awt.*;
import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author fengshuonan
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {


    /**
     * 根据字典名称获取字典列表
     *
     * @param dictName
     * @return
     */
    List<DictVo> findByDictName(String dictName);

    /**
     * 获取字典名称
     */
    String getDictName(Long dictId);


    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, String val);

    /**
     * 获取性别名称
     */
    String getSexName(Integer sex);

    /**
     * 获取银行卡类型名称
     *
     * @param cardType
     * @return
     */
    String getCardTypeName(String cardType);

    /**
     * 获取个人证件类型
     *
     * @param cardType
     * @return
     */
    String getIdCardTypeName(String cardType);

    /**
     * 获取联系人关系
     *
     * @param relation
     * @return
     */
    String getRelationName(String relation);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);


    /**
     * 查询字典
     */
    List<Dict> findInDict(Long id);

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

    /**
     * 获取指定名称下的字典列表
     *
     * @param pname
     * @return
     */
    List<Dict> getDicts(String pname);

    /**
     * 获取全局参数
     *
     * @param cfgName
     * @return
     */
    String getCfg(String cfgName);


    void cleanLocalCache();
}
