package com.easy.stl;

@SuppressWarnings("unchecked")
public class SeamlessThreadLocal<T> extends InheritableThreadLocal<T> implements Enhanced {

  @Override
  public final void set(T t) {
    Holder.addToHolder((SeamlessThreadLocal<Object>) this);
    super.set(t);
  }

  @Override
  public final T get() {
    Holder.addToHolder((SeamlessThreadLocal<Object>) this);
    return super.get();
  }

  @Override
  public final void remove() {
    Holder.removeFromHolder((SeamlessThreadLocal<Object>) this);
    super.remove();
  }
}