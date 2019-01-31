package com.microsoft.appcenter.appium;

import org.junit.runner.Description;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class EventReporter {

    private Set<String> usedIds = new HashSet<>();
    private Description lastDescription = null;
    private int labelCounter = 0;
    private int screenshotCounter = 0;
    private final Pattern paramsFinder = Pattern.compile("\\[(\\d+?)\\]");

    void reportJunit(EventType eventType, Description description, Throwable throwable) {
        int run = getRun(description);
        lastDescription = description;

        try {
            if (throwable != null) {
                StringWriter errors = new StringWriter();
                throwable.printStackTrace(new PrintWriter(errors));
                Event event = Event.createWithException(toId(description, eventType), eventType, description.getMethodName(), description.getClassName(), run, errors.toString());
                commit(event);
            } else {
                Event event = Event.create(toId(description, eventType), eventType, description.getMethodName(), description.getClassName(), run);
                commit(event);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (EventType.started.equals(eventType)) {
            labelCounter = 0;
            screenshotCounter = 0;
        }
    }

    private int getRun(Description description) {
        if(description == null) {
            throw new RuntimeException("Unable to get test information. Make sure the test class includes a @Rule:\n" +
                    "  @Rule\n" +
                    "  public TestWatcher watcher = Factory.createWatcher();");
        }

        Matcher m = paramsFinder.matcher(description.getMethodName());
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    protected abstract void commit(Event event) throws IOException;

    void reportLabel(String label, String screenshotPath, int screenshotOrientation, boolean screenshotRotated) {
        try {
            int run = getRun(lastDescription);
            Event event = Event.createLabel(labelId(), lastDescription.getMethodName(), lastDescription.getClassName(), run, label, screenshotPath, screenshotOrientation, screenshotRotated);
            commit(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void reportScreenshot(String screenshotPath, int screenshotOrientation, boolean screenshotRotated) {
        try {
            int run = getRun(lastDescription);
            Event event = Event.createScreenshot(screenshotId(), lastDescription.getMethodName(), lastDescription.getClassName(), run, screenshotPath, screenshotOrientation, screenshotRotated);
            commit(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toId(Description description, EventType eventType) {
        String id = new ShortIdentifier(String.format("j%s:%s", eventType.name().substring(0, 3), description.getDisplayName())).value();
        usedIds.add(id);
        return id;
    }

    private String labelId() {
        String id = new ShortIdentifier(String.format("jlab:%s:%d", lastDescription.getDisplayName(), labelCounter++)).value();
        usedIds.add(id);
        return id;
    }

    private String screenshotId() {
        String id = new ShortIdentifier(String.format("jscr:%s:%d", lastDescription.getDisplayName(), screenshotCounter++)).value();
        usedIds.add(id);
        return id;
    }

}
