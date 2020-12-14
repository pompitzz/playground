package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class LatchTest {
    public static void main(String[] args) throws InterruptedException {
        int users = 5;
        CountDownLatch readyLatch = new CountDownLatch(users);
        IntStream.range(0, users)
                .mapToObj(i -> createRunnable(readyLatch, i))
                .forEach(CompletableFuture::runAsync);

        log.info("모든 사용자가 Ready할 때 까지 대기");
        readyLatch.await();
        log.info("게임 시작");
    }

    private static Runnable createRunnable(CountDownLatch readyLatch, int i) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(i);
                log.info(i + "번 사용자 Ready");
                readyLatch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
}
