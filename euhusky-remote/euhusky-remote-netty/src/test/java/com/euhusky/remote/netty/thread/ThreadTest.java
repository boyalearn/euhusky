package com.euhusky.remote.netty.thread;

public class ThreadTest {
	public static ThreadLocal<String> local = new ThreadLocal<String>();//声明静态的threadlocal变量
    public static ThreadLocal<String> local2 = new ThreadLocal<String>();//声明静态的threadlocal变量
    public static void main(String [] args){
    	ThreadTest thread=new ThreadTest();
        for(int i=0;i<5;i++){
            TestThread testThread = thread.new TestThread();//创建5个线程
            new Thread(testThread).start();
        }
    }
    
    class TestThread implements Runnable{
        
        @Override
        public void run() {
            
            ThreadTest.local.set(Thread.currentThread().getId()+":"+System.currentTimeMillis());
            ThreadTest.local2.set(Thread.currentThread().getId()+"");
         // TODO Auto-generated method stub
            try {
                Thread.sleep(1l);//让线程停顿一下，便于其它线程执行
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            firstStep();
            try {
                Thread.sleep(1l);//让线程停顿一下，便于其它线程执行
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            secondStep();
            try {
                Thread.sleep(1l);//让线程停顿一下，便于其它线程执行
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            thirdStep();
            try {
                Thread.sleep(1l);//让线程停顿一下，便于其它线程执行
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            fourthStep();
            try {
                Thread.sleep(1l);//让线程停顿一下，便于其它线程执行
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            fStep();
        }
         
        public void firstStep(){
            System.out.println(Thread.currentThread().getId()+"》》》》》》"+ThreadTest.local.get().toString()+":first step");//获取本线程的threadlocal变量值并打印
        }
        public void secondStep(){
            System.out.println(ThreadTest.local.get().toString()+":second step");
        }
        public void thirdStep(){
            System.out.println(ThreadTest.local.get().toString()+":third step");
        }
        public void fourthStep(){
            System.out.println(ThreadTest.local.get().toString()+":fourth step");
        }
        public void fStep(){
            System.out.println(ThreadTest.local.get().toString()+":fifth step");
        }
    }

}
