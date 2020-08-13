package org.linwl.commonutils.tools;

import cn.hutool.core.bean.BeanUtil;
import org.linwl.commonutils.exceptions.ModelException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: JavaCommonTools
 * @description:
 * @author: linwl
 * @create: 2020-08-13 14:48
 **/
public class BeanTools {

    private BeanTools(){

    }

    /**
     * 复制对象列表
     *
     * @param sources
     * @param clazz
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> List<T> copyList(List<V> sources, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (V source : sources) {
            T target = BeanUtil.toBean(source, clazz);
            result.add(target);
        }
        return result;
    }

    /**
     * 校验DTO参数
     *
     * @param bindingResult
     * @throws ModelException
     */
    public static void checkDto(BindingResult bindingResult) throws ModelException {
        if (bindingResult.hasErrors()) {
            String message = "";
            for (ObjectError error : bindingResult.getAllErrors()) {
                message += MessageFormat.format("{0},", error.getDefaultMessage());
            }
            throw new ModelException(message);
        }
    }

    /**
     * 反射对象获取泛型
     * @param clazz class类
     * @param index 泛型所在位置
     * @return
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @return
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz) {
        return getSuperClassGenericType(clazz, 0);
    }
}
