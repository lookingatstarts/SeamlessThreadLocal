package com.easy;

import com.easy.stl.SeamlessCallable;
import com.easy.stl.SeamlessStrategy;
import com.easy.stl.SeamlessThreadLocal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

  public static void main(String[] args) throws Exception {
    SeamlessThreadLocal<String> stl = new SeamlessThreadLocal<>();
    stl.set("parent");
    Callable<Object> callable = () -> {
      System.out.println(Thread.currentThread().getName() + stl.get());
      return null;
    };
    SeamlessCallable<Object> seamlessCallable = new SeamlessCallable<>(callable, obj -> obj, SeamlessStrategy.DISSEMINATOR_MAIN);
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Future<Object> future = executorService.submit(seamlessCallable);
    future.get();
    executorService.submit(() -> stl.set("son")).get();
    stl.set("parent2");
    seamlessCallable = new SeamlessCallable<>(callable, obj -> obj, SeamlessStrategy.DISSEMINATOR_MAIN);
    executorService.submit(seamlessCallable).get();
    seamlessCallable = new SeamlessCallable<>(callable, obj -> obj, SeamlessStrategy.RECEIVER_MAIN);
    executorService.submit(seamlessCallable).get();
    executorService.shutdown();
    System.out.println(stl.get());
  }
}
