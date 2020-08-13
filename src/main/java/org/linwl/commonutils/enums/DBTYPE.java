package org.linwl.commonutils.enums;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:50
 **/
public enum DBTYPE {

    db1("db1"),db2("db2"),db3("db3");

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    DBTYPE(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
