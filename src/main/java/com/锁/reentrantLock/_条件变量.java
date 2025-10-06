package com.锁.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized中也有条件变量，waitSet。
 * reentrantLock条件变量比synchronized中也有条件变量强大之处在于，它是支持多个条件变量的
 * 使用流程
 * await前需要获得锁
 * await执行后，会释放锁，进入conditionObject等待
 * await的线程被唤醒，重新竞争lock锁
 * 竞争lock锁成功后，从await后继续执行
 *
 * */

@Slf4j
public class _条件变量 {
    static ReentrantLock lock=new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        //创建一个新的条件变量（休息室）
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        //先获得锁
        lock.lock();
        //进入休息室等待
        c1.await();

        //唤醒c1里面的某一个线程
        c1.signal();
        //唤醒c1里面的所有等待线程
        c1.signalAll();

    }
}
