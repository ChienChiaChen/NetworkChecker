package com.chiachen.netobserver;

import java.util.Collection;
import java.util.Map;

/**
 * Created by jianjiacheng on 2018/7/17.
 */

public class Collections {

    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(final Map<?, ?> map) {
        return null == map || map.isEmpty();
    }
}
