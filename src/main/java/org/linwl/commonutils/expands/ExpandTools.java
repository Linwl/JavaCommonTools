package org.linwl.commonutils.expands;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @program: JavaCommonTools
 * @description: 拓展工具类
 * @author: linwl
 * @create: 2020-08-13 14:42
 **/
public class ExpandTools {

    private ExpandTools() {}

    /**
     * Stream().filter()根据对象条件去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        System.out.println("这个函数将应用到每一个item");
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
