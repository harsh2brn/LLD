package com.ratelimiter;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter implements RateLimiter {
  private final int maxCapacity;
  private final double refillRate;
  private final ConcurrentHashMap<String, TokenBucket> userBuckets;

  public TokenBucketRateLimiter(int maxCapacity, int refillTokens, Duration refillPeriod) {
    this.maxCapacity = maxCapacity;
    this.refillRate = (double) refillTokens / refillPeriod.toNanos();
    this.userBuckets = new ConcurrentHashMap<>();
  }

  @Override
  public boolean requestAllowed(String userId) {
    TokenBucket bucket = userBuckets.computeIfAbsent(userId,
        k -> new TokenBucket(this.maxCapacity, this.refillRate));

    return bucket.tryConsume();
  }

  private static class TokenBucket {
    private final int maxCapacity;
    private double availableTokens;
    private long lastRefillTime;
    private double tokensPerNano;
    private final ReentrantLock lock;

    public TokenBucket(int maxCapacity, double refillRate) {
      this.tokensPerNano = refillRate;
      this.maxCapacity = maxCapacity;
      this.availableTokens = maxCapacity;
      this.lastRefillTime = System.nanoTime();
      this.lock = new ReentrantLock();
    }

    public boolean tryConsume() {
      lock.lock();

      try {
        refill();

        if(availableTokens >= 1.0) {
          availableTokens -= 1.0;
          return true;
        }
        return false;
      } finally {
        lock.unlock();
      }
    }

    void refill() {
      long now = System.nanoTime();
      long nanoElapsed = now - lastRefillTime;
      double tokensToAdd = nanoElapsed * tokensPerNano;

      if(tokensToAdd > 0) {
        availableTokens = Math.min(maxCapacity, availableTokens + tokensToAdd);
        lastRefillTime = now;
      }
    }
  }
}
