package com.easy.stl;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import static com.easy.stl.Holder.PLACE_OBJ;

public class SeamlessUtil {

    public static void recover(Map<SeamlessThreadLocal<Object>, Object> backupStlMap) {
        WeakHashMap<SeamlessThreadLocal<Object>, Object> currentStlMap = Holder.STL_HOLDER.get();
        currentStlMap.clear();
        if (backupStlMap == null || backupStlMap.isEmpty()) {
            return;
        }
        for (Map.Entry<SeamlessThreadLocal<Object>, Object> entry : backupStlMap.entrySet()) {
            SeamlessThreadLocal<Object> backupStl = entry.getKey();
            Object value = entry.getValue();
            backupStl.set(value);
            currentStlMap.put(backupStl, PLACE_OBJ);
        }
    }

    public static Map<SeamlessThreadLocal<Object>, Object> snapshot(Copier copier) {
        WeakHashMap<SeamlessThreadLocal<Object>, Object> currentThreadHoldStlMap = Holder.STL_HOLDER.get();
        Map<SeamlessThreadLocal<Object>, Object> snapshotMap = new HashMap<>(currentThreadHoldStlMap.size());
        for (Map.Entry<SeamlessThreadLocal<Object>, Object> entry : currentThreadHoldStlMap.entrySet()) {
            SeamlessThreadLocal<Object> stl = entry.getKey();
            snapshotMap.put(stl, copier.copy(stl.get()));
        }
        return snapshotMap;
    }

    public static void replay(Map<SeamlessThreadLocal<Object>, Object> capturedSTLMap, SeamlessStrategy seamlessStrategy) {
        WeakHashMap<SeamlessThreadLocal<Object>, Object> currentStlHolderMap = Holder.STL_HOLDER.get();
        for (Map.Entry<SeamlessThreadLocal<Object>, Object> entry : capturedSTLMap.entrySet()) {
            SeamlessThreadLocal<Object> stl = entry.getKey();
            Object value = entry.getValue();
            // 父线程和子线程都有同一个stl(stl中value可能不同)
            if (currentStlHolderMap.containsKey(stl)) {
                switch (seamlessStrategy) {
                    case RECEIVER_MAIN:
                        // 已子线程value
                        break;
                    case DISSEMINATOR_MAIN:
                        // 已父线程value
                        stl.set(value);
                        break;
                }
            } else {
                stl.set(value);
                currentStlHolderMap.put(stl, PLACE_OBJ);
            }
        }
    }
}
