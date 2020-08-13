package org.linwl.commonutils.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: JavaCommonTools
 * @description: 动态数据源
 * @author: linwl
 * @create: 2020-08-13 14:54
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    public Object determineCurrentLookupKey()
    {
        return DbContextHolder.getDbType();
    }
}
