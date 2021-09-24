package com.easy;

import com.easy.vo.Snapshot;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public interface SeamlessThreadLocal<T> extends Enhanced {

  /**
   * 使用InheritableThreadLocal是为了父线程可以传递执行上下文子线程
   */
  InheritableThreadLocal<WeakHashMap<SeamlessThreadLocal<?>, Object>> holder = new InheritableThreadLocal<WeakHashMap<SeamlessThreadLocal<?>, Object>>() {

    /***
     * 父线程传递值给子线程，浅拷贝，会破坏线程封闭性
     */
    @Override
    protected WeakHashMap<SeamlessThreadLocal<?>, Object> childValue(
        WeakHashMap<SeamlessThreadLocal<?>, Object> parentValue) {
      return new WeakHashMap<>(parentValue);
    }

    @Override
    protected WeakHashMap<SeamlessThreadLocal<?>, Object> initialValue() {
      return new WeakHashMap<>();
    }
  };

  /**
   * 快照当前线程拥有的SeamlessThreadLocal
   */
  default Snapshot snapshot() {
    WeakHashMap<SeamlessThreadLocal<?>, Object> seamlessThreadLocalMap = holder.get();
    Map<SeamlessThreadLocal<?>, Object> snapshotMap = Collections.emptyMap();
    if (seamlessThreadLocalMap != null && seamlessThreadLocalMap.size() != 0) {
      snapshotMap = new HashMap<>(seamlessThreadLocalMap.size());
      for (Entry<SeamlessThreadLocal<?>, Object> entry : seamlessThreadLocalMap.entrySet()) {
        snapshotMap.put(entry.getKey(), entry.getValue());
      }
    }
    return new Snapshot(snapshotMap);
  }

  void set(T t);

  T get();

  void remove();
}
