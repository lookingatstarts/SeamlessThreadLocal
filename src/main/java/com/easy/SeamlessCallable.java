package com.easy;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程A提交任务到线程池，任务被线程B执行
 */
public class SeamlessCallable<V> implements Enhanced, Callable<V>, Wrapper<Callable<V>> {

  private final Callable<V> callable;
  private final Map<SeamlessThreadLocal<Object>, Object> captureStlMap;
  private final SeamlessStrategy strategy;
  private final AtomicBoolean callRunFlag = new AtomicBoolean(false);

  public SeamlessCallable(Callable<V> callable, Copier copier, SeamlessStrategy strategy) {
    // 备份提交任务线程(A)的threadLocal
    this.captureStlMap = SeamlessUtil.snapshot(copier);
    this.callable = callable;
    this.strategy = strategy;
  }

  @Override
  public Callable<V> unWrapper() {
    return callable;
  }

  @Override
  public V call() throws Exception {
    if (!callRunFlag.compareAndSet(false, true)) {
      throw new IllegalStateException("SeamlessCallable只能运行一次");
    }
    // 备份执行任务线程(B)的stl
    final Map<SeamlessThreadLocal<Object>, Object> backupStlMap = SeamlessUtil.snapshot(obj -> obj);
    try {
      // 将线程A的stl复制给线程B
      SeamlessUtil.replay(captureStlMap, strategy);
      return callable.call();
    } finally {
      // 恢复执行线程B的stl
      SeamlessUtil.recover(backupStlMap);
    }
  }
}
