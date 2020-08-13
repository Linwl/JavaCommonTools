package org.linwl.commonutils.models;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:38
 **/
public class BaseMongoDbEntity {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * mongo的id
     */
    private String id;
}
