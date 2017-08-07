package com.qktang.rxAndroid.utils;

import java.lang.reflect.ParameterizedType;

/**
 *
 * Created by qktang on 2017/8/1.
 */
public class TUtil {

    public static <T> T getT(Object o, int i) {

        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }
}
