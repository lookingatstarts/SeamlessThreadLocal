package com.easy;

import java.util.concurrent.Callable;

public class SeamlessCallable<V> implements Enhanced, Callable<V>, Wrapper<Callable<V>> {

  private final Callable<V> callable;

  public SeamlessCallable(Callable<V> callable) {
    this.callable = callable;
  }

  @Override
  public Callable<V> unWrapper() {
    return callable;
  }

  @Override
  public V call() throws Exception {

    return null;
  }
}
