package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

@Slf4j
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        int threads = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threads, () -> log.info("barrier actions 수행"));

        IntStream.range(0, threads * 2)
                .forEach(i ->
                        CompletableFuture.runAsync(() -> {
                            try {
                                log.info("barrier await");
                                cyclicBarrier.await();
                            } catch (InterruptedException | BrokenBarrierException e) {
                                Thread.currentThread().interrupt();
                            }
                        })
                );

        Thread.sleep(1000);
    }
}
