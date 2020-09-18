package test;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {

    public static void main(String[] args) {
        // 弱引用 this.referent指向引用对象
        WeakReference<byte[]> weakReference = new WeakReference<>(new byte[10]);
        System.out.println(weakReference.get());
        // gc,回收弱引用
        System.gc();
        // null
        System.out.println(weakReference.get());

        /**
         * ThreadLocal中的弱引用
         * ThreadLocal中的ThreadLocalMap的Entry节点继承自WeakReference
         */
        ThreadLocal<Integer> t1 = new ThreadLocal<Integer>();
        System.out.println(t1);
        Integer i1 = 134;
        t1.set(i1);
        // 置空，gc回收new ThreadLocal对象(该对象也被WeakReference引用)
        t1 = null;
        //t1.remove();
        // 查看Thread类的threadLocals的Entry节点
        Thread thread = Thread.currentThread();

        System.gc();

        // 再次查看Thread类的threadLocals的Entry节点，弱引用被gc
        System.out.println("gc()...");
    }
}
