package com.easy.vo;

import com.easy.SeamlessThreadLocal;
import java.util.Map;

public class Snapshot {

  private final Map<SeamlessThreadLocal<?>, Object> seamlessThreadLocalSnapshot;

  public Snapshot(Map<SeamlessThreadLocal<?>, Object> seamlessThreadLocalSnapshot) {
    this.seamlessThreadLocalSnapshot = seamlessThreadLocalSnapshot;
  }
}
