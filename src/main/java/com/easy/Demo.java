package com.easy;

public class Demo {

  public static void main(String[] args)throws Exception {
    InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
    inheritableThreadLocal.set("parent");

    Thread thread = new Thread(() -> {
      System.out.println(inheritableThreadLocal.get());
      inheritableThreadLocal.set("son");
      System.out.println(inheritableThreadLocal.get());
    });
    thread.start();

    thread.join();
    System.out.println(inheritableThreadLocal.get());
  }
}
