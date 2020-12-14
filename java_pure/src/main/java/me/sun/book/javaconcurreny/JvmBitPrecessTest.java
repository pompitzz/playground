package me.sun.book.javaconcurreny;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class JvmBitPrecessTest {
    private static final int THREAD_SIZE = 30;
    private static final ExecutorService READ_EXECUTOR = Executors.newFixedThreadPool(THREAD_SIZE);
    private static final ExecutorService WRITE_EXECUTOR = Executors.newFixedThreadPool(THREAD_SIZE);

    private Long value = 30000L;

    public static void main(String[] args) throws InterruptedException {
        JvmBitPrecessTest jvmBitPrecessTest = new JvmBitPrecessTest();
        IntStream.range(0, THREAD_SIZE).forEach(i -> WRITE_EXECUTOR.execute(() -> write(jvmBitPrecessTest, i)));
        IntStream.range(0, THREAD_SIZE).forEach(i -> READ_EXECUTOR.execute(() -> read(jvmBitPrecessTest)));

        READ_EXECUTOR.shutdown();
        WRITE_EXECUTOR.shutdown();

        READ_EXECUTOR.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
        WRITE_EXECUTOR.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
    }

    private static void write(JvmBitPrecessTest jvmBitPrecessTest, int index) {
        while (true) {
            int number = index % 3;
            if (number == 0) {
                jvmBitPrecessTest.setValue(123456789);
            } else if (number == 1) {
                jvmBitPrecessTest.setValue(987654321);
            } else {
                jvmBitPrecessTest.setValue(90000000);
            }
        }
    }

    public static void read(JvmBitPrecessTest jvmBitPrecessTest) {
        while (true) {
            long value = jvmBitPrecessTest.getValue();
            if (value != 123456789 && value != 987654321 && value != 90000000) {
                log.info("{}", value);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
