package com.easy;

import java.util.WeakHashMap;

public class DefaultSeamlessThreadLocal<T> extends AbstractSeamlessThreadLocal<T>{

  private InheritableThreadLocal<T> threadLocal = new InheritableThreadLocal<>();

  @Override
  public void doSet(T t) {

  }

  @Override
  public T doGet() {
    return null;
  }

  @Override
  public void doRemove() {

  }
}
