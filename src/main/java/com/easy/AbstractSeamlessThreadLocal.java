package com.easy;

public abstract class AbstractSeamlessThreadLocal<T> implements SeamlessThreadLocal<T> {

  private static final Object PLACE_OBJ = new Object();

  @Override
  public final void set(T t) {
    addToHolder();
    doSet(t);
  }

  @Override
  public final T get() {
    addToHolder();
    return doGet();
  }

  @Override
  public final void remove() {
    removeFromHolder();
    doRemove();
  }

  public abstract void doSet(T t);

  public abstract T doGet();

  public abstract void doRemove();

  private void addToHolder() {
    if (!holder.get().containsKey(this)) {
      holder.get().put(this, PLACE_OBJ);
    }
  }

  private void removeFromHolder() {
    holder.get().remove(this);
  }
}
