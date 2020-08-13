package org.linwl.commonutils.db;

import org.linwl.commonutils.enums.DBTYPE;

/**
 * @program: JavaCommonTools
 * @description: 数据源上下文切换
 * @author: linwl
 * @create: 2020-08-13 14:49
 **/
public class DbContextHolder {

    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     *
     * @param dbTypeEnum
     */
    public static void setDbType(DBTYPE dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }

    /**
     * 取得当前数据源
     *
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /** 清除上下文数据 */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
