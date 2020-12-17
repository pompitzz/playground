package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CompletableFutureUtilsTest {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = buildExecutor(executor -> {
                executor.setCorePoolSize(200);
                executor.setMaxPoolSize(200);
            });

    @Test
    @Timeout(value = 1100, unit = TimeUnit.MILLISECONDS)
    void combineAll() throws Exception {
        List<CompletableFuture<Long>> futures = Arrays.asList(
                CompletableFuture.supplyAsync(() -> sleepAndGet(1300L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(1200L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(1100L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(1000L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(900L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(800L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(700L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(600L), threadPoolTaskExecutor),
                CompletableFuture.supplyAsync(() -> sleepAndGet(500L), threadPoolTaskExecutor)
        );

        List<Long> results = CompletableFutureUtils.combineAll(futures, 1000L, TimeUnit.MILLISECONDS);
        assertThat(results).containsOnly(500L, 600L, 700L, 800L, 900L, 1000L);
    }

    long sleepAndGet(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.info("sleep fail. time: {}", sleepTime);
        }
        return sleepTime;
    }

    ThreadPoolTaskExecutor buildExecutor(Consumer<ThreadPoolTaskExecutor> configure) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        configure.accept(threadPoolTaskExecutor);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
