package org.linwl.commonutils.models;

import java.util.List;

/**
 * @program: JavaCommonTools
 * @description: 基类
 * @author: linwl
 * @create: 2020-08-13 14:46
 **/
public class BaseTreeVo<T> {

    /**
     * 当前节点编号
     */
    private String nodeNo;

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    /**
     * 父节点编号
     */
    private String parentNo;

    /**
     * 子节点列表
     */
    public List<T> children;
}