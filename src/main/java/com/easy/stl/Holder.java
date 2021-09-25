package com.easy.stl;

import java.util.WeakHashMap;

public class Holder {

    /**
     * 使用InheritableThreadLocal是为了父线程可以传递执行上下文子线程
     */
    public static final InheritableThreadLocal<WeakHashMap<SeamlessThreadLocal<Object>, Object>> STL_HOLDER = new InheritableThreadLocal<WeakHashMap<SeamlessThreadLocal<Object>, Object>>() {

        /***
         * 父线程传递值给子线程，浅拷贝，会破坏线程封闭性
         */
        @Override
        protected WeakHashMap<SeamlessThreadLocal<Object>, Object> childValue(
                WeakHashMap<SeamlessThreadLocal<Object>, Object> parentValue) {
            return new WeakHashMap<>(parentValue);
        }

        @Override
        protected WeakHashMap<SeamlessThreadLocal<Object>, Object> initialValue() {
            return new WeakHashMap<>();
        }
    };

    public static final Object PLACE_OBJ = new Object();

    public static void addToHolder(SeamlessThreadLocal<Object> seamlessThreadLocal) {
        WeakHashMap<SeamlessThreadLocal<Object>, Object> map = STL_HOLDER.get();
        if (!map.containsKey(seamlessThreadLocal)) {
            map.put(seamlessThreadLocal, PLACE_OBJ);
        }
    }

    public static void removeFromHolder(SeamlessThreadLocal<Object> seamlessThreadLocal) {
        STL_HOLDER.get().remove(seamlessThreadLocal);
    }
}
