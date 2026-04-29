package com.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class CircuitBreaker {

  private final int failureThreshold;
  private final int successThreshold;
  private final long openStateTimeout;
  private final int maxRetries;


  private final AtomicReference<CircuitState> stateReference = new AtomicReference<>(ClosedState.INSTANCE);

  private final AtomicInteger failureCount = new AtomicInteger(0);
  private final AtomicInteger successCount = new AtomicInteger(0);
  private final AtomicInteger retryCount = new AtomicInteger(0);
  private volatile long lastFailureTime = 0;

  public CircuitBreaker(int failureThreshold, int successThreshold, long openStateTimeout, int maxRetries) {
    this.failureThreshold = failureThreshold;
    this.successThreshold = successThreshold;
    this.openStateTimeout = openStateTimeout;
    this.maxRetries = maxRetries;
  }

  public <T> T execute(Supplier<T> action) {
    CircuitState entryState = stateReference.get();
    if (!entryState.requestAllowed(this)) {
      throw new RuntimeException("Circuit breaker is open. Request not allowed.");
    }

    try {
      T result = action.get();
      entryState.recordSuccess(this);
      return result;
    } catch (Exception e) {
      entryState.recordFailure(this);
      throw e;
    } finally {
      if (entryState == HalfOpenState.INSTANCE) {
        retryCount.decrementAndGet();
      }
    }
  }

  int incrementAndGetFailureCount() {
    return failureCount.incrementAndGet();
  }

  int incrementAndGetSuccessCount() {
    return successCount.incrementAndGet();
  }

  boolean compareAndSetState(CircuitState expected, CircuitState newState) {
    return stateReference.compareAndSet(expected, newState);
  }

  long getLastFailureTime() {
    return lastFailureTime;
  }

  void recordFailureTime() {
    lastFailureTime = System.currentTimeMillis();
  }

  void resetCounters() {
    failureCount.set(0);
    successCount.set(0);
    retryCount.set(0);
  }

  boolean tryRetry() {
    while(true) {
      int currentRetryCount = retryCount.get();
      if(currentRetryCount >= maxRetries) {
        return false;
      }
      if(retryCount.compareAndSet(currentRetryCount, currentRetryCount + 1)) {
        return true;
      }
    }
  }

  public long getOpenStateTimeout() {
    return openStateTimeout;
  }

  public int getFailureThreshold() {
    return failureThreshold;
  }

  public int getSuccessThreshold() {
    return successThreshold;
  }

  public int getMaxRetries() {
    return maxRetries;
  }
}
