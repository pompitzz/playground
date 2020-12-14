package me.sun.book.javaconcurreny.cache;

import me.sun.book.javaconcurreny.JvmBitPrecessTest;

public abstract class DataCacheService {
    private Long lastKey;
    private JvmBitPrecessTest lastJvmBitPrecessTest;

    public JvmBitPrecessTest getData(Long key) {
        synchronized (this) {
            if (lastKey.equals(key)) {
                return getData(key);
            }
        }
        JvmBitPrecessTest jvmBitPrecessTest = createData(key);
        synchronized (this) {
            this.lastKey = key;
            this.lastJvmBitPrecessTest = jvmBitPrecessTest;
        }
        return jvmBitPrecessTest;
    }

    protected abstract JvmBitPrecessTest createData(long key);
}
