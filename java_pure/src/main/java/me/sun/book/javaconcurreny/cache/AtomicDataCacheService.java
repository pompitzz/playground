package me.sun.book.javaconcurreny.cache;

import me.sun.book.javaconcurreny.JvmBitPrecessTest;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicDataCacheService {
    private final AtomicReference<Long> lastKey = new AtomicReference<>();
    private final AtomicReference<JvmBitPrecessTest> lastData = new AtomicReference<>();

    public JvmBitPrecessTest getData(long key) {
        if (lastKey.get() == key) {
            return lastData.get();
        }
        JvmBitPrecessTest jvmBitPrecessTest = createData(key);
        // lastKey만 set된 시점에 해당 key와 동일한 key를 가진 메서드가 다른 스레드에서 호출되면 정확한 데이터가 전달되지 않는다.
        lastKey.set(key);
        lastData.set(jvmBitPrecessTest);
        return jvmBitPrecessTest;
    }

    protected abstract JvmBitPrecessTest createData(long key);
}
