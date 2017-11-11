package com.microsoft.appcenter.appium;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

class Watcher extends TestWatcher {

    private final EventReporter eventReporter;

    Watcher(EventReporter eventReporter) {
        super();
        this.eventReporter = eventReporter;
    }

    @Override
    protected void succeeded(Description description) {
        super.succeeded(description);
        this.eventReporter.reportJunit(EventType.succeeded, description, null);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        this.eventReporter.reportJunit(EventType.failed, description, e);
        super.failed(e, description);
    }

    @Override
    protected void skipped(org.junit.internal.AssumptionViolatedException e, Description description) {
        this.eventReporter.reportJunit(EventType.skipped, description, e);
        super.skipped(e, description);
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
        this.eventReporter.reportJunit(EventType.started, description, null);
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        this.eventReporter.reportJunit(EventType.finished, description, null);
    }

}
