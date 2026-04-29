package com.circuitbreaker;

class HalfOpenState implements CircuitState {
  public static final HalfOpenState INSTANCE = new HalfOpenState();

  private HalfOpenState() {}

  @Override
  public void recordSuccess(CircuitBreaker context) {
    if(context.incrementAndGetSuccessCount() >= context.getSuccessThreshold()) {
      if(context.compareAndSetState(this, ClosedState.INSTANCE)) {
        context.resetCounters();
      }
    }
  }

  @Override
  public void recordFailure(CircuitBreaker context) {
    if(context.compareAndSetState(this, OpenState.INSTANCE)) {
      context.recordFailureTime();
      context.resetCounters();
    }
  }

  @Override
  public boolean requestAllowed(CircuitBreaker context) {
    return context.tryRetry();
  }
}
