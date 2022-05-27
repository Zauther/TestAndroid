package io.github.zauther.android.hive.api.plugins.thread;


import io.github.zauther.android.hive.api.plugins.annotations.HiveThreadType;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HiveThreadManager {

    public static void runOnIO(Runnable task) {
        runOnIO(task, HiveThreadType.current);
    }

    public static void runOnIO(Runnable task, HiveThreadType returnOn) {
        new Thread(task, "IO").start();
    }

    public static void runOnUI(Runnable task) {
        runOnUI(task, HiveThreadType.current);
    }

    public static void runOnUI(Runnable task, HiveThreadType returnOn) {

    }




    public static void run(Runnable onRun, Runnable onReturn, HiveThreadType runOn, HiveThreadType returnOn) {
        Scheduler runOnScheduler = getScheduler(runOn);
        Scheduler returnOnScheduler = getScheduler(returnOn);

        Observable<Runnable> observable = Observable.just(onRun);
        if (runOnScheduler != null) {
            observable = observable.subscribeOn(runOnScheduler);
        }
        if (returnOnScheduler != null) {
            observable = observable.observeOn(returnOnScheduler);
        }
        observable.flatMap(new Function<Runnable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Runnable runnable) throws Throwable {
                runnable.run();
                return null;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                onReturn.run();
            }
        });
    }


    public static void runOn(Runnable task, HiveThreadType runOn) {
        Scheduler runOnScheduler = getScheduler(runOn);

        Observable<Runnable> observable = Observable.just(task);
        if (runOnScheduler != null) {
            observable = observable.subscribeOn(runOnScheduler);
        }
        observable.map(new Function<Runnable, Object>() {
            @Override
            public Object apply(Runnable runnable) throws Throwable {
                runnable.run();
                return new Object();
            }
        }).subscribe();
    }

    public static void returnOn(Runnable task, HiveThreadType returnOn){
        Observable<Runnable> observable = Observable.just(task);
        Scheduler returnOnScheduler = getScheduler(returnOn);
        if (returnOnScheduler != null) {
            observable = observable.observeOn(returnOnScheduler);
        }
        observable.subscribe(new Consumer<Runnable>() {
            @Override
            public void accept(Runnable runnable) throws Throwable {
                runnable.run();
            }
        });
    }


    public static Scheduler getScheduler(HiveThreadType threadType) {
        switch (threadType) {
            case io:
                return Schedulers.io();
            case ui:
                return AndroidSchedulers.mainThread();
            case computation:
                return Schedulers.computation();
            case newThread:
                return Schedulers.newThread();
        }
        return null;
    }
}
