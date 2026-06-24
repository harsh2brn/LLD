package com.ratelimiter;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter{
  private final int maxRequests;
  private final ConcurrentHashMap<String, Window> userWindows;
  private final long windowSizeInMillis;

  public FixedWindowRateLimiter(int maxRequests, Duration windowSize) {
    this.maxRequests = maxRequests;
    this.windowSizeInMillis = windowSize.toMillis();
    this.userWindows = new ConcurrentHashMap<>();
  }

  @Override
  public boolean requestAllowed(String userId) {
    long currentWindowKey = System.currentTimeMillis() / windowSizeInMillis;
    Window userWindow = userWindows.compute(userId, (key, existingWindow) -> {
      if(existingWindow == null || existingWindow.windowKey != currentWindowKey) {
        return new Window(currentWindowKey);
      }
      return existingWindow;
    });

    return userWindow.requestCount.incrementAndGet() <= maxRequests;
  }

  private static class Window {
    final long windowKey;
    final AtomicInteger requestCount;

    Window(long windowKey) {
      this.windowKey = windowKey;
      this.requestCount = new AtomicInteger(0);
    }
  }
}
