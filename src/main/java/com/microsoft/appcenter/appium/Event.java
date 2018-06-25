package com.microsoft.appcenter.appium;

class Event {

    Event(String id, EventType type, String testName, String className, int run) {
        this.id = id;
        this.type = type;
        this.testName = testName;
        this.className = className;
        this.run = run;
        this.timestamp = System.currentTimeMillis() / 1000.0;
    }

    public static Event createLabel(String id, String testName, String className, int run, String label, String screenshotPath, int screenshotOrientation, boolean screenshotRotated) {
        if (null == label || label.length() < 1 || label.length() > 128) {
            throw new IllegalArgumentException("Labels must be non-empty strings of length <= 128");
        }
        Event event = new Event(id, EventType.label, testName, className, run);
        event.screenshotPath = screenshotPath;
        event.label = label;
        event.screenshotOrientation = screenshotOrientation;
        event.screenshotRotated = screenshotRotated;
        return event;
    }

    public static Event createScreenshot(String id, String testName, String className, int run, String screenshotPath, int screenshotOrientation, boolean screenshotRotated) {
        Event event = new Event(id, EventType.screenshot, testName, className, run);
        event.screenshotPath = screenshotPath;
        event.screenshotOrientation = screenshotOrientation;
        event.screenshotRotated = screenshotRotated;
        return event;
    }

    public static Event createWithException(String id, EventType type, String testName, String className, int run, String exception) {
        Event event = new Event(id, type, testName, className, run);
        event.exception = exception;
        return event;
    }

    public static Event create(String id, EventType type, String testName, String className, int run) {
        return new Event(id, type, testName, className, run);
    }

    private final String id;
    private final EventType type;
    private final String testName;
    private final String className;
    private final double timestamp;
    private final int run;

    private String screenshotPath;
    private int screenshotOrientation;
    private boolean screenshotRotated;
    private String label;
    private String exception;

    public String getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public String getTestName() {
        return testName;
    }

    public String getClassName() {
        return className;
    }

    public int getRun() {
        return run;
    }

    public String getLabel() {
        return label;
    }

    public String getScreenshotPath() {
        return screenshotPath;
    }

    public int getScreenshotOrientation() {
        return screenshotOrientation;
    }

    public String getException() {
        return exception;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public boolean isScreenshotRotated() {
        return screenshotRotated;
    }
}

