package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureUtils {
    public static <T> List<T> combineAll(List<CompletableFuture<T>> futures, Long time, TimeUnit timeUnit) {
        Assert.notNull(time, "time should not be null.");
        Assert.notNull(timeUnit, "timeUnit should not be null.");

        if (CollectionUtils.isEmpty(futures)) {
            return Collections.emptyList();
        }

        try {
            CompletableFuture<Void> combineFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
            combineFutures.get(time, timeUnit);
        } catch (TimeoutException e) {
            log.info("timeout exception");
        } catch (Exception e) {
            log.info("some error", e);
        } finally {
            return getDoneFutures(futures);
        }
    }

    private static <T> List<T> getDoneFutures(List<CompletableFuture<T>> futures) {
        return futures.stream()
                .map(CompletableFutureUtils::getIfDone)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static <T> T getIfDone(CompletableFuture<T> future) {
        try {
            if (future.isDone()) {
                return future.join();
            }
            future.cancel(true);
            return null;
        } catch (Exception e) {
            log.info("error!!. {}", e.getMessage());
            return null;
        }
    }
}
