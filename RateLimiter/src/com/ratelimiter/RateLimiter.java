package com.ratelimiter;

public interface RateLimiter {
  boolean requestAllowed(String clientId);
}
