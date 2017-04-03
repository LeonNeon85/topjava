package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kpks on 29.03.2017.
 */

public class Counter {

//    private static AtomicInteger id = new AtomicInteger(0);
    private static int id;

    public static int getId() {
//        int v;
//        do {
//            v = id.get();
//        } while (!id.compareAndSet(v, v + 1));
//         return v + 1;
        id += 1;
        return id;
    }
}
