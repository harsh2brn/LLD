package com.circuitbreaker;

class OpenState implements CircuitState {
  public static final OpenState INSTANCE = new OpenState();

  private OpenState() {}
  @Override
  public void recordSuccess(CircuitBreaker context) {

  }

  @Override
  public void recordFailure(CircuitBreaker context) {
    context.recordFailureTime();
  }

  @Override
  public boolean requestAllowed(CircuitBreaker context) {
    if(System.currentTimeMillis() - context.getLastFailureTime() > context.getOpenStateTimeout()) {
      if(context.compareAndSetState(this, HalfOpenState.INSTANCE)) {
        context.resetCounters();
        return context.tryRetry();
      }
    }

    return false;
  }
}
