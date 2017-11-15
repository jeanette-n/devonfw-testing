package com.capgemini.ntc.test.core;

public interface TestObserver {

    public void onTestSuccess();
    public void onTestFailure();
    public void onTestFinish();
}
