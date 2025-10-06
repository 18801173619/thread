package com.锁.reentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public class _条件变量使用例子 {
    static ReentrantLock lock=new ReentrantLock();
    static boolean hasCigarette=false;
    static boolean hasTakeout=false;

    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            lock.lock();
            try{
                while(!hasCigarette){
                    log.debug("没有烟，先歇会");
                    try {
                        c1.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                log.debug("有烟，开始干活");
            }finally {
                lock.unlock();
            }
        },"小南").start();

        new Thread(()->{
            lock.lock();
            try{
                while(!hasTakeout){
                    log.debug("没有外卖，先歇会");
                    try {
                        c2.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
                log.debug("有外卖，开始干活");
            }finally {
                lock.unlock();
            }
        },"小女").start();

        sleep(1000);

        new Thread(()->{
            lock.lock();
            try{
                hasTakeout=true;
                c2.signal();
            }finally {
                lock.unlock();
            }
        },"送外卖的").start();

        new Thread(()->{
            lock.lock();
            try{
                hasCigarette=true;
                c1.signal();
            }finally {
                lock.unlock();
            }
        },"送烟的").start();
    }


}
