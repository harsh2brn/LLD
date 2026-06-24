public class CacheEntry<V> {
  private final long expirationTimeInNanos;
  private final V value;

  public CacheEntry(V value, long expirationTimeMillis) {
    this.value = value;
    this.expirationTimeInNanos = System.nanoTime() + expirationTimeMillis * 1_000_000L; // Convert milliseconds to nanoseconds
  }

  public long getExpirationTimeInNanos() {
    return expirationTimeInNanos;
  }

  public boolean isExpired() {
    return System.nanoTime() > expirationTimeInNanos;
  }

  public V getValue() {
    return value;
  }
}
