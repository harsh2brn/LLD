package com.circuitbreaker;

interface CircuitState {
  void recordSuccess(CircuitBreaker context);
  void recordFailure(CircuitBreaker context);
  boolean requestAllowed(CircuitBreaker context);
}
