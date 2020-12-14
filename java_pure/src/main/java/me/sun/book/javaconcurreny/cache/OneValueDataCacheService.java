package me.sun.book.javaconcurreny.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.sun.book.javaconcurreny.JvmBitPrecessTest;

import java.util.Objects;

public abstract class OneValueDataCacheService {
    private volatile OneValueCache oneValueCache = new OneValueCache(null, null);

    public JvmBitPrecessTest getData(Long key) {
        JvmBitPrecessTest jvmBitPrecessTest = oneValueCache.getData();
        if (Objects.isNull(jvmBitPrecessTest)) {
            jvmBitPrecessTest = createData(key);
            oneValueCache = new OneValueCache(key, jvmBitPrecessTest);
        }
        return jvmBitPrecessTest;
    }

    protected abstract JvmBitPrecessTest createData(long key);
}

@Getter
@RequiredArgsConstructor
class OneValueCache {
    private final Long lastKey;
    private final JvmBitPrecessTest lastJvmBitPrecessTest;

    public JvmBitPrecessTest getData() {
        if (Objects.isNull(lastJvmBitPrecessTest)) {
            return null;
        }
        return lastJvmBitPrecessTest;
    }
}
