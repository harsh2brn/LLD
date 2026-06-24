import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache <K, V> {
  private final ConcurrentHashMap<K, CacheEntry<V>> cacheMap;
  private final ScheduledExecutorService cleanUpExecutor;

  public Cache(long cleanUpIntervalInMillis) {
    this.cacheMap = new ConcurrentHashMap<>();
    if(cleanUpIntervalInMillis > 0) {
      this.cleanUpExecutor = Executors.newSingleThreadScheduledExecutor(r ->{
        Thread backgroundThread = new Thread(r, "Cache-Cleanup-Thread");
        backgroundThread.setDaemon(true);
        return backgroundThread;
      });

      cleanUpExecutor.scheduleAtFixedRate(
          this::cleanUpExpiredEntries,
          cleanUpIntervalInMillis,
          cleanUpIntervalInMillis,
          TimeUnit.MILLISECONDS);
    }
    else {
      this.cleanUpExecutor = null;
    }
  }

  private void cleanUpExpiredEntries() {
    for(K key : cacheMap.keySet()) {
      cacheMap.compute(key, (k, entry) -> {
        if(entry != null && entry.isExpired()) {
          return null;
        }

        return entry;
      });
    }
  }

  public void put(K key, V value, long ttlInMillis) {
    cacheMap.put(key, new CacheEntry<>(value, ttlInMillis));
  }

  public V get(K key) {
    CacheEntry<V> entry = cacheMap.get(key);
    if(entry == null) {
      return null;
    }
    if(entry.isExpired()) {
      cacheMap.remove(key, entry);
      return null;
    }
    return entry.getValue();
  }

  public void cleanUpShutdown() {
    if(cleanUpExecutor != null) {
      cleanUpExecutor.shutdown();
      try {
        cleanUpExecutor.awaitTermination(5, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    cacheMap.clear();
  }
}
