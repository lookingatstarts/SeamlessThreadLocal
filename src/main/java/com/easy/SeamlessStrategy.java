package com.easy;

/**
 * 线程A 提交任务 被线程B 执行
 * 当线程A和线程B同时拥有同一个stl时，stl的值取谁的
 */
public enum SeamlessStrategy {

    /**
     * 取线程A
     */
    DISSEMINATOR_MAIN,

    /**
     * 取线程B
     */
    RECEIVER_MAIN,
}
