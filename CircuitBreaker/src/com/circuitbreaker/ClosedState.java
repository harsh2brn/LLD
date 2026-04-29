package com.circuitbreaker;

class ClosedState implements CircuitState {
  public static final ClosedState INSTANCE = new ClosedState();

  private ClosedState() {}

  @Override
  public void recordSuccess(CircuitBreaker context) {
    context.resetCounters();
  }

  @Override
  public void recordFailure(CircuitBreaker context) {
    context.recordFailureTime();
    if(context.incrementAndGetFailureCount() >= context.getFailureThreshold()) {
      context.compareAndSetState(this, OpenState.INSTANCE);
    }
  }

  @Override
  public boolean requestAllowed(CircuitBreaker context) {
    return true;
  }

}
