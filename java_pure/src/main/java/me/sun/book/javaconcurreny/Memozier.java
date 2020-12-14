package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Slf4j
public class Memozier<S, T> {
    private final Computable<S, T> computable;
    private final Map<S, Future<T>> cache;

    public Memozier(Computable<S, T> computable) {
        this.cache = new ConcurrentHashMap<>();
        this.computable = computable;
    }

    public T compute(S source) {
        Future<T> future = cache.get(source);
        if (Objects.isNull(future)) {
            FutureTask<T> futureTask = new FutureTask<>(() -> computable.compute(source));
            // 단일 연산 메서드를 활용하여 안전성을 보장받을 수 있다.(동시에 해당 메서드를 호출해도 가장 먼저 put한 futureTask가 들어간다)
            cache.putIfAbsent(source, futureTask);
            future = cache.get(source);
            futureTask.run();
        }
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

interface Computable<S, T> {
    T compute(S source);
}
