package org.linwl.commonutils.tools;

import org.linwl.commonutils.models.BaseTreeVo;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: JavaCommonTools
 * @description: 树工具
 * @author: linwl
 * @create: 2020-08-13 14:46
 **/
public class TreeTools {

    private TreeTools() {}

    /**
     * 生成树
     *
     * @param sources
     * @param <T>
     * @return
     */
    public static <T extends BaseTreeVo> List<T> createTree(List<T> sources) {
        List<T> parents = new ArrayList<>();
        List<T> parentList =
                sources
                        .parallelStream()
                        .filter(p -> ObjectUtils.isEmpty(p.getParentNo()))
                        .collect(Collectors.toList());
        for (T parentNode : parentList) {
            parentNode.setChildren(createChildren(parentNode, sources));
            parents.add(parentNode);
        }
        return parents;
    }

    private static <T extends BaseTreeVo> List<T> createChildren(T parentNode, List<T> sources) {
        List<T> childs =
                sources
                        .parallelStream()
                        .filter(ch -> parentNode.getNodeNo().equals(ch.getParentNo()))
                        .collect(Collectors.toList());
        List<T> childrens = new ArrayList<>();
        for (T chlidren : childs) {
            chlidren.setChildren(createChildren(chlidren, sources));
            childrens.add(chlidren);
        }
        return childrens;
    }
}
